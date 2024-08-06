package com.polarbookshop.catalogservice.domain

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive

data class Book (
    @field:Pattern(
        regexp = "^([0-9]{10}|[0-9]{13})$",
        message = "Invalid ISBN. Must have 10 or 13 digits"
    )
    val isbn: String,

    @field:NotBlank(message = "Title is required")
    val title: String,

    @field:NotBlank(message = "Author is required")
    val author: String,

    @field:Positive(message = "Price must be greater than zero")
    val price: Double
)
