package com.polarbookshop.catalogservice.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester


@JsonTest
class BookJsonTest {
    @Autowired private lateinit var json: JacksonTester<Book>

    @Test
    fun serializeTest(){
        val book = Book("1234567890", "Title", "Author", 9.90)
        val jsonContent = json.write(book)
        assertThat(jsonContent).extractingJsonPathValue("@.isbn")
            .isEqualTo(book.isbn)
        assertThat(jsonContent).extractingJsonPathValue("@.title")
            .isEqualTo(book.title)
        assertThat(jsonContent).extractingJsonPathValue("@.author")
            .isEqualTo(book.author)
        assertThat(jsonContent).extractingJsonPathValue("@.price")
            .isEqualTo(book.price)
    }

    @Test
    fun deSerializeTest() {
        val content = """
                {
                    "isbn": "1234567890",
                    "title": "Title",
                    "author": "Author",
                    "price": 9.90
                }
                """
        val bookOrigin = Book("1234567890", "Title", "Author", 9.90)

        val bookExpected = json.parse(content)

        assertThat(bookExpected)
            .usingRecursiveComparison()
            .isEqualTo(bookOrigin)

    }
}