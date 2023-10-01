package com.sample.cleanarch.core.port

import com.sample.cleanarch.core.model.response.UserResponseDsModel

interface UserFindByIdDsGateway {

    suspend fun findByIdAndType(id: String, type: String): UserResponseDsModel?
}