package com.vraj.cityconnect.service

import com.vraj.cityconnect.config.AwsProperties
import com.vraj.cityconnect.enums.Country
import com.vraj.cityconnect.exception.EventNotFoundException
import com.vraj.cityconnect.model.Event
import com.vraj.cityconnect.repository.EventRepository
import com.vraj.cityconnect.request.AddUpdateEventRequest
import com.vraj.cityconnect.request.DeleteEventRequest
import com.vraj.cityconnect.response.BooleanResponseBody
import com.vraj.cityconnect.response.GetEventsResponse
import com.vraj.cityconnect.util.Constants.EVENT_ADD_SUCCESSFUL
import com.vraj.cityconnect.util.Constants.EVENT_DELETE_SUCCESSFUL
import com.vraj.cityconnect.util.Constants.EVENT_UPDATE_SUCCESSFUL
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import software.amazon.awssdk.core.sync.RequestBody
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.CreateTopicRequest
import software.amazon.awssdk.services.sns.model.PublishRequest
import java.util.*

interface EventService {

    fun addEvent(request: AddUpdateEventRequest): BooleanResponseBody

    fun getAllEvents(): GetEventsResponse

    fun updateEvent(request: AddUpdateEventRequest): BooleanResponseBody

    fun deleteEvent(request: DeleteEventRequest): BooleanResponseBody
}

@Service
class EventServiceImpl(
    private val eventRepository: EventRepository,
    private val s3Client: S3Client,
    private val awsProperties: AwsProperties,
    private val snsClient: SnsClient
) : EventService {

    override fun addEvent(request: AddUpdateEventRequest): BooleanResponseBody {
        val imageUrl = request.image?.let { uploadImageToS3(it) }
        val savedEvent = eventRepository.save(request.toEvent(imageUrl))
        sendEventNotification(savedEvent)
        return BooleanResponseBody.success(EVENT_ADD_SUCCESSFUL)
    }

    override fun getAllEvents(): GetEventsResponse {
        val events = eventRepository.findAll()
        return GetEventsResponse.success(events)
    }

    override fun updateEvent(request: AddUpdateEventRequest): BooleanResponseBody {
        val event = eventRepository.findById(request.id)
            .orElseThrow { EventNotFoundException() }

        request.image?.let {
            event.imageUrl?.let { deleteImageFromS3(it) }
        }

        val imageUrl = request.image?.let { uploadImageToS3(it) } ?: event.imageUrl
        val updatedEvent = event.copy(
            title = request.title,
            description = request.description,
            startTime = request.startTime,
            endTime = request.endTime,
            country = request.country,
            imageUrl = imageUrl
        )
        eventRepository.save(updatedEvent)
        return BooleanResponseBody.success(EVENT_UPDATE_SUCCESSFUL)
    }

    override fun deleteEvent(request: DeleteEventRequest): BooleanResponseBody {
        val event = eventRepository.findById(request.id)
            .orElseThrow { EventNotFoundException() }
        event.imageUrl?.let { deleteImageFromS3(it) }
        eventRepository.delete(event)
        return BooleanResponseBody.success(EVENT_DELETE_SUCCESSFUL)
    }

    private fun uploadImageToS3(file: MultipartFile): String {
        val key = "images/${UUID.randomUUID()}-${file.originalFilename}"
        s3Client.putObject(
            PutObjectRequest.builder()
                .bucket(awsProperties.bucketName)
                .key(key)
                .build(),
            RequestBody.fromInputStream(file.inputStream, file.size)
        )

        return "https://${awsProperties.bucketName}.s3.${awsProperties.region}.amazonaws.com/$key"
    }

    private fun deleteImageFromS3(imageUrl: String) {
        val key = imageUrl.substringAfter("${awsProperties.bucketName}.s3.${awsProperties.region}.amazonaws.com/")
        s3Client.deleteObject { builder ->
            builder.bucket(awsProperties.bucketName).key(key)
        }
    }

    private fun sendEventNotification(event: Event) {
        val snsTopicArn = ensureTopicExists(event.country)
        val message = "New event added in ${event.country}: ${event.title}. Check it out now!"
        snsClient.publish(
            PublishRequest.builder()
                .topicArn(snsTopicArn)
                .message(message)
                .subject("CityConnect - ${event.title}")
                .build()
        )
    }

    private fun ensureTopicExists(country: Country): String {
        val topicArnPrefix = "arn:aws:sns:${awsProperties.region}:${awsProperties.accountId}:"
        val topicName = "CityConnect-${country.name}"
        val existingTopicArn = snsClient.listTopics().topics()
            .find { it.topicArn().startsWith(topicArnPrefix + topicName) }
            ?.topicArn()

        return existingTopicArn ?: run {
            snsClient.createTopic(
                CreateTopicRequest.builder()
                    .name(topicName)
                    .build()
            ).topicArn()
        }
    }
}
