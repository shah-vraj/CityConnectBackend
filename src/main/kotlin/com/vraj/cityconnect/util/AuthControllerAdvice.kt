package com.vraj.cityconnect.util

import com.vraj.cityconnect.exception.UserAlreadyRegisteredException
import com.vraj.cityconnect.exception.UserNotFoundException
import com.vraj.cityconnect.response.ErrorResponseBody
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

object AuthControllerAdvice {

    private val logger = LoggerFactory.getLogger(AuthControllerAdvice::class.java)

    fun handleUserNotFoundException(exception: UserNotFoundException): ResponseEntity<ErrorResponseBody> {
        logger.error("User not found: ${exception.message}")
        val response = ErrorResponseBody.error(exception.message.orEmpty())
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }

    fun handleUserAlreadyRegisteredException(exception: UserAlreadyRegisteredException): ResponseEntity<ErrorResponseBody> {
        logger.error("User already registered: ${exception.message}")
        val response = ErrorResponseBody.error(exception.message.orEmpty())
        return ResponseEntity(response, HttpStatus.CONFLICT)
    }
}
