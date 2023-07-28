package com.thanesh.udemy.micronaut.tutorial.config

import io.micronaut.context.annotation.ConfigurationInject
import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("\${datasources.default}")
data class DatabaseConfig @ConfigurationInject constructor(
    val username: String,
    val url: String,
    val password: String
)
