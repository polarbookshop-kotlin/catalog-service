package com.polarbookshop.catalogservice.domain

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.annotation.Version
import java.time.Instant

data class Book (
    @Id
    val id: Long?,

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
    val price: Double,

    @Version
    val version: Int,

    @CreatedDate
    val createdDate: Instant?,

    @LastModifiedDate
    val lastModifiedDate: Instant?
){
    constructor(
        isbn: String,
        title: String,
        author: String,
        price: Double
    ):this(
        null,
        isbn,
        title,
        author,
        price,
        0,
        null,
        null
    )


}
