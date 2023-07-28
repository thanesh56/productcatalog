package com.thanesh.udemy.micronaut.tutorial.config

import io.micronaut.context.annotation.ConfigurationInject
import io.micronaut.context.annotation.ConfigurationProperties

@ConfigurationProperties("my")
data class MyConfig @ConfigurationInject constructor(val name: String)