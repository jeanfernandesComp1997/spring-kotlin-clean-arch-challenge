package com.sample.cleanarch.gateway.dataprovider

import com.sample.cleanarch.shared.mapper.UserDataMapper
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface R2dbcUserRepository : ReactiveCrudRepository<UserDataMapper, String> {

    suspend fun existsByDocumentOrEmail(document: String, email: String): Boolean
    suspend fun findByIdAndType(id: String, type: String): UserDataMapper?
}