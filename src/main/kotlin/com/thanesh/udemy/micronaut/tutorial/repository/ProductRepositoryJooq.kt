package com.thanesh.udemy.micronaut.tutorial.repository

import com.thanesh.udemy.micronaut.tutorial.db.Tables
import com.thanesh.udemy.micronaut.tutorial.db.tables.records.ProductRecord
import com.thanesh.udemy.micronaut.tutorial.entity.ProductEntity
import com.thanesh.udemy.micronaut.tutorial.exception.ProductRecordNotFoundException
import jakarta.inject.Inject
import jakarta.inject.Singleton
import org.jooq.DSLContext
import java.util.concurrent.ConcurrentHashMap

@Singleton
class ProductRepositoryJooq @Inject constructor(private val dslContext: DSLContext) {
    fun save(productRecord: ProductRecord) {
        dslContext.insertInto(Tables.PRODUCT).set(productRecord).execute()
    }

    fun list(): List<ProductRecord> {
        return dslContext
            .selectFrom(Tables.PRODUCT)
            .fetchInto(ProductRecord::class.java)
    }

    fun findById(id: String): ProductRecord? {
        return dslContext
            .selectFrom(Tables.PRODUCT)
            .where(Tables.PRODUCT.ID.eq(id))
            .fetchOneInto(ProductRecord::class.java)
    }

    fun update(updatedProductEntity: ProductEntity) {
        /* val recordId = updatedProductEntity.id
         if (memory.containsKey(recordId)) {
             memory[recordId] = updatedProductEntity
         } else {
             throw ProductRecordNotFoundException("No record with $recordId exists")
         }*/
    }

    fun delete() {

    }

    fun deleteById(id: String) {
        /*if (memory.containsKey(id)) {
            memory.remove(id)
        } else {
            throw ProductRecordNotFoundException("No record with $id exist for delete")
        }*/
    }


}