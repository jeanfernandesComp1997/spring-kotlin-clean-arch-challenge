package com.sample.cleanarch.gateway.document

import com.sample.cleanarch.core.gateway.DocumentCheckerGateway
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Component
class DocumentCheckerGatewayImpl(
    @Qualifier("authenticatedWebClient")
    private val webClient: WebClient
) : DocumentCheckerGateway {

    override suspend fun retrieveDocumentRestrictions(document: String): Mono<Array<String>> {
        val params = mapOf("document" to document)
        return webClient
            .get()
            .uri {
                it.path("restrictions")
                    .query("document={document}")
                    .build(params)
            }
            .retrieve()
            .bodyToMono(Array<String>::class.java)
    }
}