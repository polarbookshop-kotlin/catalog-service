package com.polarbookshop.catalogservice.domain

import jakarta.validation.Validation
import jakarta.validation.Validator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookValidationTest {
    private lateinit var validator: Validator

    @BeforeAll
    fun setUp(){
        validator = Validation.buildDefaultValidatorFactory().validator
    }

    @Test
    fun whenAllFieldsCorrectThenValidationSucceeds(){
        val book = Book("1234567890", "Title", "Author", 9.90)
        val violations = validator.validate(book)
        assertThat(violations).isEmpty()
    }

    @Test
    fun whenIsbnNotDefinedThenValidationFails(){
        val book = Book("", "Title", "Author", 9.90)
        val validations = validator.validate(book)
        val message = "Invalid ISBN. Must have 10 or 13 digits"
        assertThat(validations).hasSize(1)
        val constraintViolationMessages = validations
                        .stream()
            .map { it.message}
            .toList()
        assertThat(constraintViolationMessages).contains(message)

    }

    @Test
    fun  whenIsbnDefinedButIncorrectThenValidationFails(){
        val book = Book("a123456789", "Title", "Author", 9.90)
        val validations = validator.validate(book)
        val message = "Invalid ISBN. Must have 10 or 13 digits"
        assertThat(validations).hasSize(1)
        val constraintViolationMessages = validations
            .stream()
            .map { it.message}
            .toList()
        assertThat(constraintViolationMessages).contains(message)
    }

    @Test
    fun whenTitleIsNotDefinedThenValidationFails(){
        val book = Book("0123456789", "", "Author", 9.90)
        val validations = validator.validate(book)
        val message = "Title is required"
        assertThat(validations).hasSize(1)
        val constraintViolationMessages = validations
            .stream()
            .map { it.message}
            .toList()
        assertThat(constraintViolationMessages).contains(message)
    }

    @Test
    fun whenAuthorIsNotDefinedThenValidationFails(){
        val book = Book("0123456789", "Title", "", 9.90)
        val validations = validator.validate(book)
        val message = "Author is required"
        assertThat(validations).hasSize(1)
        val constraintViolationMessages = validations
            .stream()
            .map { it.message}
            .toList()
        assertThat(constraintViolationMessages).contains(message)
    }

    @Test
    fun whenPriceIsDefinedButNotGreaterThanZeroThenValidationFails(){
        val book = Book("0123456789", "Title", "Author", -1.0)
        val validations = validator.validate(book)
        val message = "Price must be greater than zero"
        assertThat(validations).hasSize(1)
        val constraintViolationMessages = validations
            .stream()
            .map { it.message}
            .toList()
        assertThat(constraintViolationMessages).contains(message)
    }

    @Test
    fun whenPriceIsDefinedButZeroThenValidationFails(){
        val book = Book("0123456789", "Title", "Author", 0.0)
        val validations = validator.validate(book)
        val message = "Price must be greater than zero"
        assertThat(validations).hasSize(1)
        val constraintViolationMessages = validations
            .stream()
            .map { it.message}
            .toList()
        assertThat(constraintViolationMessages).contains(message)
    }
}