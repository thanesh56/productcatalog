package com.thanesh.udemy.micronaut.tutorial.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller
class HelloWorldController {

    @Get("/hello")
    fun hello(): String {
        return "Hello World"
    }
}