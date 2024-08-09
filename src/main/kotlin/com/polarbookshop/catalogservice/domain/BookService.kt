package com.polarbookshop.catalogservice.domain

import org.springframework.stereotype.Service

@Service
class BookService(private val repository: BookRepository) {
    fun viewBookList(): Iterable<Book> = repository.findAll()
    fun viewBookDetails(isbn: String): Book = repository.findByIsbn(isbn) ?: throw BookNotFoundException(isbn)
    fun addBookToCatalog(book: Book): Book {
        repository.existsByIsbn(book.isbn)
            .takeIf { it }
            ?.let { throw BookAlreadyExistsException(book.isbn) }
        return repository.save(book)
    }
    fun removeBookFromCatalog(isbn: String) = repository.deleteByIsbn(isbn)
    fun editBookDetails(isbn: String, book: Book): Book {
        return repository.findByIsbn(isbn)
            ?.let {
                val bookToUpdate = Book(
                    it.id,
                    isbn,
                    book.title,
                    book.author,
                    book.price,
                    it.version,
                    it.createdDate,
                    it.lastModifiedDate
                )
                repository.save(bookToUpdate)
            }
            ?: addBookToCatalog(book)

    }

}