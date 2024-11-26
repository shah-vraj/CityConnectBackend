package com.vraj.cityconnect.util

import com.vraj.cityconnect.exception.EventNotFoundException
import com.vraj.cityconnect.exception.UserAlreadyRegisteredException
import com.vraj.cityconnect.exception.UserNotFoundException
import com.vraj.cityconnect.response.ErrorResponseBody
import jakarta.validation.ConstraintViolationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalControllerAdvice {

    // region: GeneralControllerAdvice
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleValidationExceptions(exception: ConstraintViolationException): ResponseEntity<ErrorResponseBody> =
        GeneralControllerAdvice.handleValidationExceptions(exception)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponseBody> =
        GeneralControllerAdvice.handleMethodArgumentNotValid(exception)

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(exception: Exception): ResponseEntity<ErrorResponseBody> =
        GeneralControllerAdvice.handleGlobalException(exception)
    // end region

    // Region: AuthControllerAdvice
    @ExceptionHandler(UserNotFoundException::class)
    fun handleUserNotFoundException(exception: UserNotFoundException): ResponseEntity<ErrorResponseBody> =
        AuthControllerAdvice.handleUserNotFoundException(exception)

    @ExceptionHandler(UserAlreadyRegisteredException::class)
    fun handleUserAlreadyRegisteredException(exception: UserAlreadyRegisteredException): ResponseEntity<ErrorResponseBody> =
        AuthControllerAdvice.handleUserAlreadyRegisteredException(exception)
    // end region

    // Region: EventControllerAdvice
    @ExceptionHandler(EventNotFoundException::class)
    fun handleEventNotFoundException(exception: EventNotFoundException): ResponseEntity<ErrorResponseBody> =
        EventControllerAdvice.handleEventNotFoundException(exception)
}
