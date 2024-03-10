package com.sample.cleanarch.gateway.document

import com.sample.cleanarch.core.gateway.DocumentCheckerGateway
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

@Component
class DocumentCheckerGatewayImpl(
    @Qualifier("authenticatedWebClient")
    private val webClient: WebClient
) : DocumentCheckerGateway {

    override suspend fun retrieveDocumentRestrictions(document: String): List<String> {
        val params = mapOf("document" to document)
        return webClient
            .get()
            .uri {
                it.path("restrictions")
                    .query("document={document}")
                    .build(params)
            }
            .retrieve()
            .awaitBody()
    }

    override fun retrieveDocumentRestrictionsSimulatingBlockingLibrary(document: String): List<String> {
        val params = mapOf("document" to document)
        var result = webClient
            .get()
            .uri {
                it.path("restrictions")
                    .query("document={document}")
                    .build(params)
            }
            .retrieve()
            .bodyToMono(Any::class.java)
            .block()

        result = result as List<String>
        return result
    }

    override fun retrieveDocumentRestrictionsFlux(document: String): Mono<List<String>> {
        val params = mapOf("document" to document)
        return webClient
            .get()
            .uri {
                it.path("restrictions")
                    .query("document={document}")
                    .build(params)
            }
            .retrieve()
            .bodyToMono()
    }
}