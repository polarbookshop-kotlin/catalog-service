package com.polarbookshop.catalogservice.domain

import com.polarbookshop.catalogservice.web.BookController
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

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
}