package com.sample.cleanarch.application.configuration

import com.sample.cleanarch.core.dto.Token
import io.netty.handler.logging.LogLevel
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.ExchangeFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.netty.http.client.HttpClient
import reactor.netty.transport.logging.AdvancedByteBufFormat

@Configuration
class WebClientConfig {

    val logger: Logger = LoggerFactory.getLogger(WebClientConfig::class.java)

    @Primary
    @Bean
    fun defaultWebClient(): WebClient {
        return WebClient
            .builder()
            .baseUrl("http://localhost:9000")
            .build()
    }

    @Bean
    fun authenticatedWebClient(): WebClient {
        val httpClient = HttpClient
            .create()
            .wiretap(
                "reactor.netty.http.client.HttpClient",
                LogLevel.INFO, AdvancedByteBufFormat.TEXTUAL
            )

        return WebClient
            .builder()
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .baseUrl("http://localhost:9000")
            .filter(this::setSessionToken)
            .build()
    }

    private fun setSessionToken(request: ClientRequest, exchangeFunction: ExchangeFunction): Mono<ClientResponse> {
        return retrieveSessionToken()
            .flatMap { token ->
                val clientRequest = ClientRequest.from(request)
                    .headers { it.setBearerAuth(token) }
                    .build()
                exchangeFunction.exchange(clientRequest)
            }
    }

    private fun retrieveSessionToken(): Mono<String> {
        return Mono.fromCallable {
            tokenBlockingCodeLibrarySimulateGenerateToken()
        }.subscribeOn(Schedulers.boundedElastic())
    }

    private fun tokenBlockingCodeLibrarySimulateGenerateToken(): String {
        logger.info("Getting token in thread: ${Thread.currentThread().name}")
        return defaultWebClient()
            .get()
            .uri("/token")
            .retrieve()
            .bodyToMono(Token::class.java)
            .block()
            ?.accessToken ?: throw RuntimeException("Error on getting access token.")
    }

    private fun setSessionTokenAsync(request: ClientRequest, exchangeFunction: ExchangeFunction): Mono<ClientResponse> {
        return tokenNonBlockingCodeLibrarySimulateGenerateToken()
            .flatMap { token ->
                val clientRequest = ClientRequest.from(request)
                    .headers { it.setBearerAuth(token.accessToken) }
                    .build()
                exchangeFunction.exchange(clientRequest)
            }
    }

    private fun tokenNonBlockingCodeLibrarySimulateGenerateToken(): Mono<Token> {
        logger.info("Getting token in thread: ${Thread.currentThread().name}")
        return defaultWebClient()
            .get()
            .uri("/token")
            .retrieve()
            .bodyToMono(Token::class.java)
    }
}