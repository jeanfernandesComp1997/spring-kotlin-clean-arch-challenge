package com.sample.cleanarch.core.gateway

import com.sample.cleanarch.core.domain.dto.CreateUserDataSourceDto

interface UserRegisterDataSourceGateway {

    suspend fun existsByDocumentOrEmail(document: String, email: String): Boolean

    suspend fun save(createUserDataSource: CreateUserDataSourceDto)
}