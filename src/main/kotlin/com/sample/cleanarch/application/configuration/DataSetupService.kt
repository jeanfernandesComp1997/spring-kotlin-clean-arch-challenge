package com.sample.cleanarch.application.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.core.io.Resource
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.stereotype.Service
import org.springframework.util.StreamUtils
import java.nio.charset.StandardCharsets

@Service
class DataSetupService(
    @Value("classpath:h2/init.sql") private val initSql: Resource,
    @Value("\${database.h2.active}") private val h2DatabaseIsActive: Boolean,
    private val entityTemplate: R2dbcEntityTemplate
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        if (h2DatabaseIsActive) {
            val query = StreamUtils.copyToString(initSql.inputStream, StandardCharsets.UTF_8)
            entityTemplate
                .databaseClient
                .sql(query)
                .then()
                .subscribe()
        }
    }
}