package com.sample.cleanarch.core.gateway

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface DocumentCheckerGateway {

    suspend fun retrieveDocumentRestrictions(document: String): List<String>

    fun retrieveDocumentRestrictionsFlux(document: String): Mono<List<String>>

    fun retrieveDocumentRestrictionsSimulatingBlockingLibrary(document: String): List<String>
}