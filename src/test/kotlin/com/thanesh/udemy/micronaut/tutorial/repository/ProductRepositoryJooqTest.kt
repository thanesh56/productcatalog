package com.thanesh.udemy.micronaut.tutorial.repository

import com.thanesh.udemy.micronaut.tutorial.util.TestDataUtil.Companion.getProductEntity
import com.thanesh.udemy.micronaut.tutorial.util.TestDataUtil.Companion.toRecord
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@MicronautTest
class ProductRepositoryJooqTest {

    @Inject
    lateinit var productRepositoryJooq: ProductRepositoryJooq

    @BeforeEach
    fun cleanup() {
        productRepositoryJooq.delete()
    }

    @Test
    fun canSaveProductJooq() {
        val productEntity = getProductEntity()
        productRepositoryJooq.save(productEntity.toRecord())
        val result = productRepositoryJooq.findById(productEntity.id)
        assertNotNull(result)
        assertEquals(productEntity.toRecord(), result)
    }

    @Test
    fun `list() function returns correct records`() {
        val productFRecordList = (1..2).map { getProductEntity().toRecord() }
        productFRecordList.forEach { productRecord ->
            productRepositoryJooq.save(productRecord)
        }
        val result = productRepositoryJooq.list()
        println("result = $result")
        assertTrue(result.isNotEmpty())
        assertEquals(productFRecordList.size, result.size)
        assertEquals(productFRecordList.sortedBy { it.id }, result.sortedBy { it.id })
    }
}