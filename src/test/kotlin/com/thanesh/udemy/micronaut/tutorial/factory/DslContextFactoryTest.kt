package com.thanesh.udemy.micronaut.tutorial.factory

import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.jooq.DSLContext
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

@MicronautTest
class DslContextFactoryTest {

    @Inject
    lateinit var application: EmbeddedApplication<*>

    @Test
    fun canGetDslContext() {
        val dslContext = application.applicationContext.getBean(DSLContext::class.java)
        assertNotNull(dslContext)
    }
}