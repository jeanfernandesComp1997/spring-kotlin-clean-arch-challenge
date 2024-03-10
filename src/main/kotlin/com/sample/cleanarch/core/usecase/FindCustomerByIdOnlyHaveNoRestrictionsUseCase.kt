package com.sample.cleanarch.core.usecase

import com.sample.cleanarch.core.domain.dto.CustomerDto
import reactor.core.publisher.Mono

interface FindCustomerByIdOnlyHaveNoRestrictionsUseCase {

    suspend fun execute(customerId: String): CustomerDto

    fun executeFlux(customerId: Mono<String>): Mono<CustomerDto>
}