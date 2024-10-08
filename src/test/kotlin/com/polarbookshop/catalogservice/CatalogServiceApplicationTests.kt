package com.polarbookshop.catalogservice

import com.polarbookshop.catalogservice.domain.Book
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class CatalogServiceApplicationTests
    (@Autowired private val webTestClient: WebTestClient) {

    @Test
    fun contextLoads() {
    }



    @Test
    fun whenGetRequestWithIdThenBookReturned(){
        val bookIsbn = "1231231230"
        val bookToCreate = Book(bookIsbn, "Title", "Author", 9.90, null)
        val book = webTestClient
            .post()
            .uri("/books")
            .bodyValue(bookToCreate)
            .exchange()
            .expectStatus().isCreated
            .expectBody(Book::class.java).value { assertThat(it).isNotNull() }
            .returnResult().responseBody
        webTestClient.get().uri("/books/$bookIsbn")
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(Book::class.java).value {
                assertThat(it).isNotNull
                assertThat(it.isbn).isEqualTo(bookIsbn)
            }

    }

    @Test
    fun whenPostRequestThenBookCreated() {
        val expectedBook = Book("1231231231", "Title", "Author", 9.90, null)

        webTestClient
            .post()
            .uri("/books")
            .bodyValue(expectedBook)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(Book::class.java).value { actualBook ->
                assertThat(actualBook).isNotNull()
                assertThat(actualBook.isbn).isEqualTo(expectedBook.isbn)
            }
    }

    @Test
    fun whenPutRequestThenBookUpdated() {
        val bookIsbn = "1231231232"
        val bookToCreate = Book(bookIsbn, "Title", "Author", 9.90, null)
        val createdBook: Book = webTestClient
            .post()
            .uri("/books")
            .bodyValue(bookToCreate)
            .exchange()
            .expectStatus().isCreated()
            .expectBody(Book::class.java).value { book -> assertThat(book).isNotNull() }
            .returnResult().responseBody as Book
        val bookToUpdate = Book(createdBook.isbn, createdBook.title, createdBook.author, 7.95, null)

        webTestClient
            .put()
            .uri("/books/$bookIsbn")
            .bodyValue(bookToUpdate)
            .exchange()
            .expectStatus().isOk()
            .expectBody(Book::class.java).value { actualBook ->
                assertThat(actualBook).isNotNull()
                assertThat(actualBook.price).isEqualTo(bookToUpdate.price)
            }
    }

    @Test
    fun whenDeleteRequestThenBookDeleted() {
        val bookIsbn = "1231231233"
        val bookToCreate = Book(bookIsbn, "Title", "Author", 9.90, null)
        webTestClient
            .post()
            .uri("/books")
            .bodyValue(bookToCreate)
            .exchange()
            .expectStatus().isCreated()

        webTestClient
            .delete()
            .uri("/books/$bookIsbn")
            .exchange()
            .expectStatus().isNoContent()

        webTestClient
            .get()
            .uri("/books/$bookIsbn")
            .exchange()
            .expectStatus().isNotFound()
            .expectBody(String::class.java).value { errorMessage: String? ->
                assertThat(errorMessage).isEqualTo(
                    "{\"message\":\"Book with ISBN $bookIsbn not found\"}"
                )
            }
    }


}
