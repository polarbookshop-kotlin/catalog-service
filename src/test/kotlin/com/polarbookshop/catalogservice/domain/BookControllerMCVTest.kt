package com.polarbookshop.catalogservice.domain

import com.polarbookshop.catalogservice.web.BookController
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(BookController::class)
class BookControllerMCVTest {
    @MockBean private lateinit var bookService: BookService
    @Autowired private lateinit var  mockMvc: MockMvc

    @Test
    fun whenGetBookNotExistsThenShouldReturn404(){
        val isbn = "7373731394"
        given(bookService.viewBookDetails(isbn)).willThrow(BookNotFoundException(isbn))
        mockMvc.perform(get("/books/$isbn"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun whenPostMalformedJsonThenShouldReturn400() {
        val malformedJson = """
            {
                "isbn": "1234567890",
                "title": "Example Book",
                "author": "Author Name",
                "price": "invalid_price"
            }
        """.trimIndent()

        mockMvc.perform(post("/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(malformedJson))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message").value("Malformed JSON request."))

    }

    @Test
    fun whenPostInvalidDataThenShouldReturn400() {
        val invalidDataJson = """
            {
                "isbn": "",
                "title": "Example Book",
                "author": "Author Name",
                "price": -10.0
            }
        """.trimIndent()

        mockMvc.perform(post("/books")
            .contentType(MediaType.APPLICATION_JSON)
            .content(invalidDataJson))
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.price").value("Price must be greater than zero"))
    }
}