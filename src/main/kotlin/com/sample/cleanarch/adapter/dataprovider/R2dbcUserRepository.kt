package com.sample.cleanarch.adapter.dataprovider

import com.sample.cleanarch.adapter.mapper.UserDataMapper
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface R2dbcUserRepository : ReactiveCrudRepository<UserDataMapper, String> {

    fun existsByDocumentOrEmail(document: String, email: String): Mono<Boolean>
    fun existsByDocument(document: String): Mono<Boolean>
    fun findByIdAndType(id: String, type: String): Mono<UserDataMapper>
}