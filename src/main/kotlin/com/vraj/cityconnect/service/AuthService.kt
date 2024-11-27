package com.vraj.cityconnect.service

import com.vraj.cityconnect.config.AwsProperties
import com.vraj.cityconnect.enums.Country
import com.vraj.cityconnect.exception.PasswordNotMatchingException
import com.vraj.cityconnect.exception.UserAlreadyRegisteredException
import com.vraj.cityconnect.exception.UserNotFoundException
import com.vraj.cityconnect.repository.UserRepository
import com.vraj.cityconnect.request.LoginRequest
import com.vraj.cityconnect.request.RegisterRequest
import com.vraj.cityconnect.response.LoginResponse
import com.vraj.cityconnect.response.RegisterResponse
import com.vraj.cityconnect.util.PasswordEncoder
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.sns.SnsClient
import software.amazon.awssdk.services.sns.model.CreateTopicRequest
import software.amazon.awssdk.services.sns.model.SubscribeRequest

interface AuthService {

    fun registerUser(request: RegisterRequest): RegisterResponse

    fun loginUser(request: LoginRequest): LoginResponse
}

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val awsProperties: AwsProperties,
    private val snsClient: SnsClient
) : AuthService {

    override fun registerUser(request: RegisterRequest): RegisterResponse {
        if (userRepository.existsByEmail(request.email))
            throw UserAlreadyRegisteredException(request.email)

        userRepository.save(request.toUser(passwordEncoder))
        subscribeToEventNotifications(request.country, request.email)
        return RegisterResponse.success()
    }

    override fun loginUser(request: LoginRequest): LoginResponse {
        userRepository.findByEmail(request.email)?.let {
            if (!passwordEncoder.checkPassword(request.password, it.password))
                throw PasswordNotMatchingException(request.email)
        } ?: throw UserNotFoundException(request.email)
        return LoginResponse.success()
    }

    private fun subscribeToEventNotifications(country: Country, email: String) {
        val snsTopicArn = ensureTopicExists(country)
        snsClient.subscribe(
            SubscribeRequest.builder()
                .protocol("email")
                .endpoint(email)
                .topicArn(snsTopicArn)
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
