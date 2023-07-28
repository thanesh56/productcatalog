package com.thanesh.udemy.micronaut.tutorial.entity

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.model.naming.NamingStrategies
import java.math.BigDecimal

@MappedEntity(value = "product", namingStrategy = NamingStrategies.UnderScoreSeparatedLowerCase::class)
data class ProductEntity(
    @field:Id

    val id:String,
    val productName: String,
    val description: String,
    val imageURL: String? = null,
    val unitPrice: BigDecimal,
    val internalId: String? = null
)