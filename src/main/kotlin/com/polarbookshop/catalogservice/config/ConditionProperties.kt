package com.polarbookshop.catalogservice.config

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "condition.polar.testdata")
class ConditionProperties {
    /**
     * enable condition demo
     * */
    lateinit var enabled: String
}