package com.thanesh.udemy.micronaut.tutorial.controller

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

@MicronautTest
class HelloWorldControllerTest {

    @Inject
    @field:Client("/")
    lateinit var httpClient: HttpClient

    @Test
    fun testHello() {
        val httpGet = HttpRequest.GET<String>("/hello")
        val result = httpClient.toBlocking().retrieve(httpGet)
        assertTrue(result.isNotBlank())
        assertEquals("Hello World", result)
        println("result = $result")
    }
}