package com.sample.cleanarch.core.gateway

import com.sample.cleanarch.core.domain.dto.UserDataSourceDto
import reactor.core.publisher.Mono

interface UserFindByIdDataSourceGateway {

    suspend fun findByIdAndType(id: String, type: String): UserDataSourceDto?

    fun findByIdAndTypeFlux(id: String, type: String): Mono<UserDataSourceDto>
}