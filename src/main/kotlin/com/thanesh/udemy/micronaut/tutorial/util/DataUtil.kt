package com.thanesh.udemy.micronaut.tutorial.util

import com.thanesh.udemy.micronaut.tutorial.db.tables.records.ProductRecord
import com.thanesh.udemy.micronaut.tutorial.dto.ProductDto
import com.thanesh.udemy.micronaut.tutorial.entity.ProductEntity
import java.util.*
import kotlin.random.Random

class DataUtil private constructor() {
    companion object {
        fun getProductEntity() = ProductEntity(
            id = UUID.randomUUID().toString(),
            productName = "Test product name",
            description = "Some desc",
            unitPrice = Random.nextInt(10, 1000).toBigDecimal(),
            imageURL = "http://img.jpg",
            internalId = UUID.randomUUID().toString()
        )

        fun ProductEntity.toDto(): ProductDto {
            return ProductDto(
                id = this.id,
                productName = this.productName,
                description = this.description,
                unitPrice = this.unitPrice,
                imageURL = this.imageURL
            )
        }

        fun ProductDto.toEntity(): ProductEntity {
            return ProductEntity(
                id = this.id,
                productName = this.productName,
                description = this.description,
                unitPrice = this.unitPrice,
                imageURL = this.imageURL,
                internalId = UUID.randomUUID().toString()
            )
        }

        fun ProductDto.toRecord(): ProductRecord {
            val productDto = this
            return ProductRecord().apply {
                this.id = productDto.id
                this.productName = productDto.productName
                this.description = productDto.description
                this.unitPrice = productDto.unitPrice.toLong()
                this.imageUrl = productDto.imageURL
                this.internalId = UUID.randomUUID().toString()
            }
        }

        fun ProductEntity.toRecord(): ProductRecord {
            val entity = this
            return ProductRecord().apply {
                this.id = entity.id
                this.productName = entity.productName
                this.description = entity.description
                this.unitPrice = entity.unitPrice.toLong()
                this.imageUrl = entity.imageURL
                this.internalId = entity.internalId
            }
        }
    }
}