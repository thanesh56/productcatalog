package com.thanesh.udemy.micronaut.tutorial.repository

import com.thanesh.udemy.micronaut.tutorial.entity.ProductEntity
import com.thanesh.udemy.micronaut.tutorial.exception.ProductRecordNotFoundException
import com.thanesh.udemy.micronaut.tutorial.util.TestDataUtil
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

@MicronautTest
class ProductRepositoryJdbcTest {
    @Inject
    lateinit var productRepository: ProductRepositoryJdbc

    @BeforeEach
    fun cleanup() {
        productRepository.deleteAll()
    }

    @Test
    fun canSaveProductEntity() {
        val productEntity = TestDataUtil.getProductEntity()
        productRepository.save(productEntity)
        val result = productRepository.findById(productEntity.id)
        println("result = $result")
        assertTrue(result.isPresent)
        assertEquals(productEntity, result.get())
    }

    @Test
    fun `list() function returns correct records`() {
        val productEntityList = (1..2).map { TestDataUtil.getProductEntity() }
        productEntityList.forEach { productEntity ->
            productRepository.save(productEntity)
        }

        /*productEntityList.forEach { productEntity ->
            val productEntityOptional = productRepository.findById(productEntity.id)
            println("productEntityOptional: ${productEntityOptional.get()}")
        }*/

        val tempResult = productRepository.findAll()

        val result = mutableListOf<ProductEntity>()
        result.addAll(tempResult)
        println("result = $result")
        assertNotNull(result)
        assertEquals(productEntityList, result.toList())
    }

    @Test
    fun `update() function modifies the correct record with correct value`() {
        val productEntity = TestDataUtil.getProductEntity()
        productRepository.save(productEntity)

        /*val updatedProductEntity = ProductEntity(
            id = productEntity.id,
            productName = "Thaneshwar",
            description = "Here we are testing update method",
            unitPrice = productEntity.unitPrice,
            imageURL = productEntity.imageURL,
            internalId = productEntity.internalId
        )*/

        //or

        val updatedProductEntity = productEntity.copy(
            productName = "Thaneshwar",
            description = "Here we are testing update method",
        )
        productRepository.update(updatedProductEntity)
        val result = productRepository.findById(productEntity.id)
        println("result = $result")
        assertEquals(updatedProductEntity, result)


        val nonExistingRecord = updatedProductEntity.copy(id = UUID.randomUUID().toString())
        try {
            productRepository.update(nonExistingRecord)
            org.junit.jupiter.api.fail("Update for non existing record should throw exception")
        } catch (e: Exception) {
            assertEquals("No record with ${nonExistingRecord.id} exists", e.message)
            assertEquals(ProductRecordNotFoundException::class.java, e::class.java)
        }
    }


    @Test
    fun `deleteById() removes the correct from the database`() {
        val productEntityList = (1..2).map { TestDataUtil.getProductEntity() }
        productEntityList.forEach { productEntity ->
            productRepository.save(productEntity)
        }
        val recordToDelete = productEntityList[0]

        productRepository.deleteById(recordToDelete.id)
        val nullResponse = productRepository.findById(recordToDelete.id)
        assertNull(nullResponse)
        assertNotNull(productRepository.findById(productEntityList[1].id))
        val recordId = UUID.randomUUID().toString()
        try {
            productRepository.deleteById(recordId)
            org.junit.jupiter.api.fail("Delete for non existing record should through exception")
        } catch (e: Exception) {
            assertEquals("No record with $recordId exist for delete", e.message)
            assertEquals(ProductRecordNotFoundException::class.java, e::class.java)
        }
    }
}