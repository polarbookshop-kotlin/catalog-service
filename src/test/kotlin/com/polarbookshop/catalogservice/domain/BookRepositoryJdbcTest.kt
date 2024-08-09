package com.polarbookshop.catalogservice.domain

import com.polarbookshop.catalogservice.config.DataConfig
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.Import
import org.springframework.data.jdbc.core.JdbcAggregateTemplate
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles

@DataJdbcTest
@Import(DataConfig::class)
@AutoConfigureTestDatabase(
    replace = AutoConfigureTestDatabase.Replace.NONE
)
@ActiveProfiles("integration")
class BookRepositoryJdbcTest (
    @Autowired private val bookRepository: BookRepository,
    @Autowired private val jdbcAggregateTemplate: JdbcAggregateTemplate
){
    @Test
    @Rollback
    fun findBookByIsbnWhenExisting(){
        val bookIsbn = "1234567899"
        val book = Book(bookIsbn, "title", "author", 1.2)
        jdbcAggregateTemplate.insert(book)
        val findByIsbn = bookRepository.findByIsbn(bookIsbn)
        assertThat(findByIsbn).isNotNull
        assertThat(findByIsbn!!.isbn).isEqualTo(bookIsbn)

    }

    @Test
    @Rollback
    fun deleteBookByIsbnWhenExisting(){
        val bookIsbn = "1234567899"
        val book = Book(bookIsbn, "title", "author", 1.2)
        jdbcAggregateTemplate.insert(book)
        val findByIsbn = bookRepository.deleteByIsbn(bookIsbn)
        assertThat(findByIsbn).isNotNull

        val findByIsbnByFind = bookRepository.findByIsbn(bookIsbn)
        assertThat(findByIsbnByFind).isNull()
    }
}