package com.vraj.cityconnect.service

import com.vraj.cityconnect.exception.EventNotFoundException
import com.vraj.cityconnect.repository.EventRepository
import com.vraj.cityconnect.request.AddUpdateEventRequest
import com.vraj.cityconnect.request.DeleteEventRequest
import com.vraj.cityconnect.response.BooleanResponseBody
import com.vraj.cityconnect.response.GetEventsResponse
import com.vraj.cityconnect.util.Constants.EVENT_ADD_SUCCESSFUL
import com.vraj.cityconnect.util.Constants.EVENT_DELETE_SUCCESSFUL
import com.vraj.cityconnect.util.Constants.EVENT_UPDATE_SUCCESSFUL
import org.springframework.stereotype.Service

interface EventService {

    fun addEvent(request: AddUpdateEventRequest): BooleanResponseBody

    fun getAllEvents(): GetEventsResponse

    fun updateEvent(request: AddUpdateEventRequest): BooleanResponseBody

    fun deleteEvent(request: DeleteEventRequest): BooleanResponseBody
}

@Service
class EventServiceImpl(
    private val eventRepository: EventRepository
) : EventService {

    override fun addEvent(request: AddUpdateEventRequest): BooleanResponseBody {
        eventRepository.save(request.toEvent())
        return BooleanResponseBody.success(EVENT_ADD_SUCCESSFUL)
    }

    override fun getAllEvents(): GetEventsResponse {
        val events = eventRepository.findAll()
        return GetEventsResponse.success(events)
    }

    override fun updateEvent(request: AddUpdateEventRequest): BooleanResponseBody {
        val event = eventRepository.findById(request.id)
            .orElseThrow { EventNotFoundException() }
        val updatedEvent = event.copy(
            title = request.title,
            description = request.description,
            startTime = request.startTime,
            endTime = request.endTime,
            country = request.country,
        )
        eventRepository.save(updatedEvent)
        return BooleanResponseBody.success(EVENT_UPDATE_SUCCESSFUL)
    }

    override fun deleteEvent(request: DeleteEventRequest): BooleanResponseBody {
        val event = eventRepository.findById(request.id)
            .orElseThrow { EventNotFoundException() }
        eventRepository.delete(event)
        return BooleanResponseBody.success(EVENT_DELETE_SUCCESSFUL)
    }
}
