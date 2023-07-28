package com.thanesh.udemy.micronaut.tutorial.controller

import com.thanesh.udemy.micronaut.tutorial.dto.ProductDto
import com.thanesh.udemy.micronaut.tutorial.entity.ProductEntity
import com.thanesh.udemy.micronaut.tutorial.repository.ProductRepository
import com.thanesh.udemy.micronaut.tutorial.repository.ProductRepositoryJdbc
import com.thanesh.udemy.micronaut.tutorial.util.DataUtil.Companion.toEntity
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import jakarta.inject.Inject
import java.util.*


@Controller("/products")
class ProductController @Inject constructor(
    private val productRepository: ProductRepository,
    private val productRepositoryJdbc: ProductRepositoryJdbc

) {

    @Get("/{id}")
    fun get(id: String): HttpResponse<ProductEntity?>? {
        val record = productRepository.findById(id)
        return HttpResponse.ok(record)
    }

    @Get("/jdbc/{id}")
    fun getWithJdbc(id: String): HttpResponse<ProductEntity?>? {
        val record = productRepositoryJdbc.findById(id)
        return if (record.isPresent) {
            HttpResponse.ok(record.get())
        } else {
            HttpResponse.badRequest()
        }
    }

    @Get("/")
    fun list(): HttpResponse<List<ProductEntity>> {
        val records = productRepository.list()
        return HttpResponse.ok(records)
    }

    @Post("/")
    fun save(@Body productDto: ProductDto): HttpResponse<String> {
        productRepository.save(productDto.toEntity())
        return HttpResponse.created("Saved")
    }

    @Post("/jdbc/")
    fun saveWithJdbc(@Body productDto: ProductDto): HttpResponse<String> {
        productRepositoryJdbc.save(productDto.toEntity())
        return HttpResponse.created("Saved")
    }

    @Put("/")
    fun update(@Body productDto: ProductDto): HttpResponse<String> {
        productRepository.update(productDto.toEntity())
        return HttpResponse.ok("Updated")
    }

    @Delete("/{id}")
    fun delete(id: String): HttpResponse<Any> {
        productRepository.deleteById(id)
        return HttpResponse.noContent()
    }

}