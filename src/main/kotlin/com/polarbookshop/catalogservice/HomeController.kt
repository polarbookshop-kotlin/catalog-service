package com.polarbookshop.catalogservice

import com.polarbookshop.catalogservice.config.PolarProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {
    @Autowired private lateinit var polarProperties: PolarProperties

    @GetMapping("/")
    fun home() = polarProperties.greeting
}