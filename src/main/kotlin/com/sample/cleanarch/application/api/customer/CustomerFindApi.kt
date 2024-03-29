package com.sample.cleanarch.application.api.customer

import com.sample.cleanarch.core.domain.annotation.ValidUUID
import com.sample.cleanarch.core.domain.dto.CustomerDto
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import reactor.core.publisher.Mono

@RequestMapping("customers")
@Validated
interface CustomerFindApi {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun findById(@PathVariable("id") @Valid @ValidUUID id: String): CustomerDto

    @GetMapping("/{id}/no-restrictions")
    @ResponseStatus(HttpStatus.OK)
    suspend fun findByIdOnlyHaveNoRestrictions(@PathVariable("id") @Valid @ValidUUID id: String): CustomerDto

    @GetMapping("/{id}/no-restrictions-flux")
    @ResponseStatus(HttpStatus.OK)
    fun findByIdOnlyHaveNoRestrictionsFlux(@PathVariable("id") @Valid @ValidUUID id: String): Mono<CustomerDto>
}