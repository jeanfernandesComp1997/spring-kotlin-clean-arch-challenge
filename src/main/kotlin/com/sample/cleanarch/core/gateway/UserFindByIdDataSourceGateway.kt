package com.sample.cleanarch.core.gateway

import com.sample.cleanarch.core.dto.UserDataSourceDto

interface UserFindByIdDataSourceGateway {

    suspend fun findByIdAndType(id: String, type: String): UserDataSourceDto?
}