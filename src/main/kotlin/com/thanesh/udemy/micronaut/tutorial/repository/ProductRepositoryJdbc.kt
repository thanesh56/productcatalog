package com.thanesh.udemy.micronaut.tutorial.repository

import com.thanesh.udemy.micronaut.tutorial.entity.ProductEntity
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.annotation.Query
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.CrudRepository

@JdbcRepository(dialect = Dialect.MYSQL)
interface ProductRepositoryJdbc : CrudRepository<ProductEntity, String> {

    @Query("SELECT * FROM PRODUCT")
    fun list(): List<ProductEntity>
}