package com.sample.cleanarch.core.gateway

import reactor.core.publisher.Mono

interface DocumentCheckerGateway {

    suspend fun retrieveDocumentRestrictions(document: String): Mono<Array<String>>
}