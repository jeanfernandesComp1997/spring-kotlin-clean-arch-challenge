package com.sample.cleanarch.core.port

import com.sample.cleanarch.core.model.request.UserRequestDsModel

interface UserRegisterDsGateway {

    suspend fun existsByDocumentOrEmail(document: String, email: String): Boolean

    suspend fun save(userRequestDsModel: UserRequestDsModel)
}