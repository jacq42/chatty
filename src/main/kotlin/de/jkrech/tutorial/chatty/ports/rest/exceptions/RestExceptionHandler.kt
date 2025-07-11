package de.jkrech.tutorial.chatty.ports.rest.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.client.ResourceAccessException
import reactor.core.publisher.Mono
import java.net.URI

@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(ResourceAccessException::class)
    fun handleResourceAccessException(exception: ResourceAccessException): ResponseEntity<*> {
        return createProblemDetailResponseEntity(
            httpStatus = HttpStatus.NOT_FOUND,
            title = "Not found",
            detail = "Could not connect to resource: ${exception.message}"
        )
    }

    @ExceptionHandler(SecurityException::class)
    fun handleSecurityException(exception: SecurityException): ResponseEntity<*> {
        return createProblemDetailResponseEntity(
            httpStatus = HttpStatus.FORBIDDEN,
            title = "Forbidden",
            detail = "${exception.message}"
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleAnyException(exception: Exception): Mono<ResponseEntity<Any>> =
        Mono.defer {
            Mono.just(createProblemDetailResponseEntity(
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR
            ))
        }

    private fun createProblemDetailResponseEntity(
        httpStatus: HttpStatus,
        type: URI = ABOUT_BLANK_URI,
        title: String = httpStatus.reasonPhrase,
        detail: String? = null,
    ): ResponseEntity<Any> {
        val problemDetail = ProblemDetail.forStatus(httpStatus)
        problemDetail.type = type
        problemDetail.title = title
        problemDetail.detail = detail
        return ResponseEntity(problemDetail, httpStatus)
    }

    companion object {
        val ABOUT_BLANK_URI: URI = URI.create("about:blank")
    }
}