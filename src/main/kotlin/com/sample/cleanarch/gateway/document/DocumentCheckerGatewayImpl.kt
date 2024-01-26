package com.sample.cleanarch.gateway.document

import com.sample.cleanarch.core.gateway.DocumentCheckerGateway
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

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
}