package com.thanesh.udemy.micronaut.tutorial.repository

import com.thanesh.udemy.micronaut.tutorial.entity.ProductEntity
import com.thanesh.udemy.micronaut.tutorial.exception.ProductRecordNotFoundException
import jakarta.inject.Singleton
import java.util.concurrent.ConcurrentHashMap

@Singleton
class ProductRepository {
    private val memory = ConcurrentHashMap<String, ProductEntity>()
    fun save(productEntity: ProductEntity) {
        memory[productEntity.id] = productEntity
    }

    fun list(): List<ProductEntity> {
        // return memory.values.toList()
        //above is not working so we use below code

        val result = mutableListOf<ProductEntity>()
        result.addAll(memory.values)
        return result.toList()
    }

    fun findById(id: String): ProductEntity? {
        return memory[id]
    }

    fun update(updatedProductEntity: ProductEntity) {
        val recordId = updatedProductEntity.id
        if (memory.containsKey(recordId)) {
            memory[recordId] = updatedProductEntity
        } else {
            throw ProductRecordNotFoundException("No record with $recordId exists")
        }
    }

    fun delete() {

    }

    fun deleteById(id: String) {
        if (memory.containsKey(id)) {
            memory.remove(id)
        } else {
            throw ProductRecordNotFoundException("No record with $id exist for delete")
        }
    }


}