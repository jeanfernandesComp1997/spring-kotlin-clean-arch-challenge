package com.sample.cleanarch.application.rest.controller.exceptionhandler

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun handleException(exception: Exception): ResponseEntity<Any> {
        return ResponseEntity
            .internalServerError()
            .body(object {
                val message = exception.message
                val stackTrace = exception.stackTrace
            })
    }
}