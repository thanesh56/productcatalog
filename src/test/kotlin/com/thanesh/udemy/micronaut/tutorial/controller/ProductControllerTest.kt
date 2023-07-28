package com.thanesh.udemy.micronaut.tutorial.controller

import com.thanesh.udemy.micronaut.tutorial.dto.ProductDto
import com.thanesh.udemy.micronaut.tutorial.repository.ProductRepository
import com.thanesh.udemy.micronaut.tutorial.repository.ProductRepositoryJdbc
import com.thanesh.udemy.micronaut.tutorial.util.TestDataUtil.Companion.getProductEntity
import com.thanesh.udemy.micronaut.tutorial.util.TestDataUtil.Companion.toDto
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal


@MicronautTest
class ProductControllerTest {

    @Inject
    lateinit var productRepository: ProductRepository

    @Inject
    lateinit var productRepositoryJdbc: ProductRepositoryJdbc

    @Inject
    @field:Client("/products")
    lateinit var httpClient: HttpClient

    @Test
    fun `get() return correct record`() {
        val productEntity = getProductEntity()
        productRepository.save(productEntity)

        val httpGet = HttpRequest.GET<Any>("/${productEntity.id}")
        val result = httpClient.toBlocking().retrieve(httpGet, ProductDto::class.java)
        println("result = $result")
        assertNotNull(result)
        assertEquals(productEntity.toDto(), result)
    }

    @Test
    fun `getWithJdbc() return correct record`() {
        val productDto = getProductEntity().toDto()
        val httpPost = HttpRequest.POST("/jdbc/", productDto)
        val responseFromPost = httpClient.toBlocking().exchange(httpPost, String::class.java)
        println("responseFromPost.body() = ${responseFromPost.body()}")

        val httpGet = HttpRequest.GET<Any>("/jdbc/${productDto.id}")
        val result = httpClient.toBlocking().retrieve(httpGet, ProductDto::class.java)
        println("result = $result")
        assertNotNull(result)
        assertEquals(productDto, result)
    }

    @Test
    fun `save() saves correct record`() {
        val productDto = getProductEntity().toDto()

        val httpPost = HttpRequest.POST("/", productDto)
        val responseFromPost = httpClient.toBlocking().exchange(httpPost, String::class.java)
        println("responseFromPost.body() = ${responseFromPost.body()}")
        assertEquals(HttpStatus.CREATED, responseFromPost.status())
        assertEquals("Saved", responseFromPost.body())


        val httpGet = HttpRequest.GET<Any>("/${productDto.id}")
        val responseFromGet = httpClient.toBlocking().retrieve(httpGet, ProductDto::class.java)
        println("responseFromGet = $responseFromGet")
        assertNotNull(responseFromGet)
        bothRecordsAreEqual(productDto, responseFromGet)
    }

    @Test
    fun `update() correct record with correct attributes`() {
        val productDto = getProductEntity().toDto()

        val httpPost = HttpRequest.POST("/", productDto)
        httpClient.toBlocking().exchange(httpPost, String::class.java)

        val updatedProductDto = productDto.copy(
            productName = "Thaneshwar",
            description = "Updated by thaneshwar for testing PUT method",
            unitPrice = BigDecimal.ONE
        )


        val httpPut = HttpRequest.PUT("/", updatedProductDto)
        val responseFromPut = httpClient.toBlocking().exchange(httpPut, String::class.java)
        println("responseFromPut = $responseFromPut")
        assertNotNull(responseFromPut)
        assertEquals(HttpStatus.OK, responseFromPut.status)


        val httpGet = HttpRequest.GET<Any>("/${productDto.id}")
        val responseFromGet = httpClient.toBlocking().retrieve(httpGet, ProductDto::class.java)
        println("responseFromGet = $responseFromGet")
        assertNotNull(responseFromGet)
        bothRecordsAreEqual(updatedProductDto, responseFromGet)
    }

    @Test
    fun `delete() removes correct record db`() {
        val productDto = getProductEntity().toDto()

        val httpPost = HttpRequest.POST("/", productDto)
        httpClient.toBlocking().exchange(httpPost, String::class.java)

        val httpDelete = HttpRequest.DELETE<String>("/${productDto.id}")
        val responseFromPut = httpClient.toBlocking().exchange(httpDelete, String::class.java)
        println("responseFromDelete = $responseFromPut")
        assertNotNull(responseFromPut)
        assertEquals(HttpStatus.NO_CONTENT, responseFromPut.status)

        println("productDto_id: ${productDto.id}")


        val httpGet = HttpRequest.GET<Any>("/${productDto.id}")
        assertThrows(HttpClientResponseException::class.java) {
            httpClient.toBlocking().retrieve(httpGet, ProductDto::class.java)
        }
    }

    @Test
    fun `list() returns correct records db`() {
        val productDtoList = (1..2).map { getProductEntity().toDto() }

        productDtoList.forEach { productDto ->
            val httpPost = HttpRequest.POST("/", productDto)
            httpClient.toBlocking().exchange(httpPost, String::class.java)
        }

        val httpGet = HttpRequest.GET<Any>("/")
        val responseFromGet = httpClient.toBlocking().retrieve(httpGet, Argument.listOf(ProductDto::class.java))
        assertNotNull(responseFromGet)
        assertEquals(2, responseFromGet.size)
        //bothRecordsAreEqual(productDtoList[0],responseFromGet.first())
    }


    private fun bothRecordsAreEqual(
        productDto: ProductDto,
        responseFromGet: ProductDto
    ) {
        assertEquals(productDto.id, responseFromGet.id)
        assertEquals(productDto.productName, responseFromGet.productName)
        assertEquals(productDto.description, responseFromGet.description)
        assertEquals(productDto.imageURL, responseFromGet.imageURL)
        assertEquals(0, productDto.unitPrice.compareTo(responseFromGet.unitPrice))
    }


}