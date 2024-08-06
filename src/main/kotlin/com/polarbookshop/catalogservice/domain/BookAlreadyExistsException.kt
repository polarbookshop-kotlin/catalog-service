package com.polarbookshop.catalogservice.domain

class BookAlreadyExistsException(isbn: String) : RuntimeException("Book with ISBN $isbn already exists") {
}

