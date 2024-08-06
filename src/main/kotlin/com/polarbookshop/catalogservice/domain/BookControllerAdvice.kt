package com.polarbookshop.catalogservice.domain

import jakarta.validation.ConstraintViolationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class BookControllerAdvice {
    val logger: Logger = LoggerFactory.getLogger(BookControllerAdvice::class.java)

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): Map<String, String>{
        val errors = HashMap<String, String>()
        ex.bindingResult.allErrors.forEach{ error ->
            val fieldName = (error as FieldError).field
            errors[fieldName] = error.defaultMessage!!
        }
        return errors
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleHttpMessageNotReadableException(ex: HttpMessageNotReadableException): Map<String, String>{
        val errors = HashMap<String, String>()
        errors["message"] = "Malformed JSON request."
        return errors
    }

    @ExceptionHandler(ConstraintViolationException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleConstraintViolationException(ex: ConstraintViolationException): Map<String, String>{
        val errors = HashMap<String, String>()
        ex.constraintViolations.forEach{ violation ->
            errors[violation.propertyPath.toString()] = violation.message
        }
        return errors
    }


    @ExceptionHandler(BookNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleBookNotFoundException(ex: BookNotFoundException): Map<String, String>{
        val errors = HashMap<String, String>()
        errors["message"] = ex.message!!
        return errors
    }

    @ExceptionHandler(BookAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleBookAlreadyExistsException(ex: BookAlreadyExistsException): Map<String, String>{
        val errors = HashMap<String, String>()
        errors["message"] = ex.message!!
        return errors
    }
}