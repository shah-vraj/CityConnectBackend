package com.vraj.cityconnect.util

import com.vraj.cityconnect.exception.EventNotFoundException
import com.vraj.cityconnect.response.ErrorResponseBody
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

object EventControllerAdvice {

    private val logger = LoggerFactory.getLogger(EventControllerAdvice::class.java)

    fun handleEventNotFoundException(exception: EventNotFoundException): ResponseEntity<ErrorResponseBody> {
        logger.error("Event not found: ${exception.message}")
        val response = ErrorResponseBody.error(exception.message.orEmpty())
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }
}
