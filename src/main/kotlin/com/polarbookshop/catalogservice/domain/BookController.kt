package com.polarbookshop.catalogservice.domain

import jakarta.validation.Valid
import jakarta.validation.constraints.Pattern
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/books")
@Validated
class BookController(private val bookService: BookService) {
    @GetMapping
    fun get() = bookService.viewBookList()

    @GetMapping("/{isbn}")
    fun get(@PathVariable
            @Pattern(
                regexp = "^([0-9]{10}|[0-9]{13})$",
                message = "Invalid ISBN. Must have 10 or 13 digits")
            isbn: String) = bookService.viewBookDetails(isbn)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun post(@Valid @RequestBody book: Book) = bookService.addBookToCatalog(book)

    @DeleteMapping("/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable isbn: String) = bookService.removeBookFromCatalog(isbn)

    @PutMapping("/{isbn}")
    fun put(@PathVariable isbn: String, @RequestBody book: Book) = bookService.editBookDetails(isbn, book)
}