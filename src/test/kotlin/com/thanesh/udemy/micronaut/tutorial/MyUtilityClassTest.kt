package com.thanesh.udemy.micronaut.tutorial

import io.micronaut.context.annotation.Property
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*


@MicronautTest
class MyUtilityClassTest {

    @Inject
    lateinit var myUtilityClass: MyUtilityClass

    @Test
    fun `say hello with annotation returns correct greeting`() {
        val expectedGreeting = "Hello Thanesh"
        val greeting = myUtilityClass.sayHelloWithValueAnnotation()
        assertEquals(expectedGreeting, greeting)
    }

    @Test
    @Property(name = "my.name", value = "John")
    fun `config mapping override works`() {
        val expectedGreeting = "Hello John"
        val greeting = myUtilityClass.sayHelloWithValueAnnotation()
        assertEquals(expectedGreeting, greeting)
    }

    @Test
    fun `say hello via immutable config object returns correct greeting`() {
        val expectedGreeting = "Hello Thanesh"
        val greeting = myUtilityClass.sayHelloViaImmutableConfigObject()
        assertEquals(expectedGreeting, greeting)
    }
}