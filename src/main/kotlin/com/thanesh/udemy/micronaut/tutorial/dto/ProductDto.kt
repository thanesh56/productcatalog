package com.thanesh.udemy.micronaut.tutorial.dto

import java.math.BigDecimal

data class ProductDto(
    val id: String,
    val productName: String,
    val description: String,
    val imageURL: String? = null,
    val unitPrice: BigDecimal,
)