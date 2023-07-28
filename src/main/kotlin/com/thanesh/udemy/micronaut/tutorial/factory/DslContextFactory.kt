package com.thanesh.udemy.micronaut.tutorial.factory

import io.micronaut.context.annotation.Factory
import jakarta.inject.Inject
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import javax.sql.DataSource

@Factory
class DslContextFactory @Inject constructor(private val dataSource: DataSource) {

    fun createDslContext(): DSLContext {
        return DSL.using(dataSource, SQLDialect.MYSQL)
    }


}