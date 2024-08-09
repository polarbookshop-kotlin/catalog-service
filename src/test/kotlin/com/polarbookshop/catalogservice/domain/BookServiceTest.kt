package com.polarbookshop.catalogservice.domain

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension


@ExtendWith(MockitoExtension::class)
class BookServiceTest {
    @Mock
    private lateinit var bookRepository: BookRepository

    @InjectMocks
    private lateinit var bookService: BookService

    @Test
    fun whenBookToCrateAlreadyExistsThenThrows(){
        val bookIsbn = "1234561232"
        val bookToCreate = Book(bookIsbn, "Title", "Author", 9.90)
        `when`(bookRepository.existsByIsbn(bookIsbn)).thenReturn(true)

        assertThatThrownBy { bookService.addBookToCatalog(bookToCreate) }
            .isInstanceOf(BookAlreadyExistsException::class.java)
            .hasMessage("Book with ISBN $bookIsbn already exists")
    }

    @Test
    fun whenBookToReadDoesNotExistThenThrows(){
        val bookIsbn = "1234561232"
        `when`(bookRepository.findByIsbn(bookIsbn)).thenReturn(null)
        assertThatThrownBy { bookService.viewBookDetails(bookIsbn) }
            .isInstanceOf(BookNotFoundException::class.java)
            .hasMessage("Book with ISBN $bookIsbn not found")

    }
}