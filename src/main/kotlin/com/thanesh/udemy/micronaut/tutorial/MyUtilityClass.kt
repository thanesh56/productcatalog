package com.thanesh.udemy.micronaut.tutorial

import com.thanesh.udemy.micronaut.tutorial.config.MyConfig
import io.micronaut.context.annotation.Value
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
class MyUtilityClass {
    private val logger = LoggerFactory.getLogger(MyUtilityClass::class.simpleName)

    @Value("\${my.name}")
    lateinit var name: String

    @Inject
    lateinit var myConfig: MyConfig

    fun sayHelloWithValueAnnotation(): String {
        return "Hello $name"
    }

    fun sayHelloViaImmutableConfigObject(): String {
        return "Hello ${myConfig.name}"
    }
}