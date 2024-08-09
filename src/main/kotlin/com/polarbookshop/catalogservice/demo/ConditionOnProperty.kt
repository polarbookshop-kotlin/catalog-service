package com.polarbookshop.catalogservice.demo

import com.polarbookshop.catalogservice.domain.Book
import com.polarbookshop.catalogservice.domain.BookRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
@ConditionalOnProperty(name = ["condition.polar.testdata.enabled"],
    havingValue = "true")
class ConditionOnProperty(@Autowired private val bookRepository: BookRepository) {
    @EventListener(ApplicationReadyEvent::class)
    fun loadBookTestData() {
        logger.info { "condition profile test" }
        val book1 = Book("1234567893", "Northern Lights", "Lyra Silverstar", 9.90)
        val book2 = Book("1234567894", "Polar Journey", "Iorek Polarson", 12.90)
        bookRepository.save(book1)
        bookRepository.save(book2)
    }
}