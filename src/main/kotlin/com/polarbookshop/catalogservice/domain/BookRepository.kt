package com.polarbookshop.catalogservice.domain

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

interface BookRepository: CrudRepository<Book, Long>{
    fun findByIsbn(isbn: String): Book?
    fun existsByIsbn(isbn: String): Boolean
    @Transactional
    @Modifying
    @Query("delete from Book where isbn=:isbn")
    fun deleteByIsbn(@Param("isbn")isbn: String)

    fun deleteBookByIsbn(isbn: String): Book?

}