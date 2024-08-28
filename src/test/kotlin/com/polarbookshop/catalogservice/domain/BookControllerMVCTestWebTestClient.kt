package com.polarbookshop.catalogservice.domain

import com.polarbookshop.catalogservice.web.BookController
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(BookController::class)
class BookControllerMVCTestWebTestClient {
    @MockBean private lateinit var bookService: BookService
    @Autowired private lateinit var webTestClient: WebTestClient

    @Test
    fun `when get book not exists then should return 404`(){
        val isbn = "7373731394"
        BDDMockito.given(bookService.viewBookDetails(isbn)).willThrow(BookNotFoundException(isbn))
        webTestClient.get().uri("books/$isbn")
            .exchange()
            .expectStatus().isNotFound
    }

    @Test
    fun `when post malformed Json Then should return 400`(){
        val malformedJson = """
            {
                "isbn": "1234567890",
                "title": "Example Book",
                "author": "Author Name",
                "price": "invalid_price"
            }
        """.trimIndent()
        webTestClient.post()
            .uri("/books")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(malformedJson)
            .exchange()
            .expectStatus()
            .isBadRequest
            .expectBody()
            .jsonPath("$.message")
            .isEqualTo("Malformed JSON request.")

    }

    @Test
    fun `when post invalid data then should return 400`(){
        val invalidDataJson = """
            {
                "isbn": "",
                "title": "Example Book",
                "author": "Author Name",
                "price": -10.0
            }
        """.trimIndent()

        webTestClient.post()
            .uri("/books")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(invalidDataJson)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody().jsonPath("$.price").isEqualTo("Price must be greater than zero")
    }

}