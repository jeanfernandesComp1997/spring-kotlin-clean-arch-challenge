package com.sample.cleanarch.core.usecase

import com.sample.cleanarch.core.domain.dto.CustomerDto

interface FindCustomerByIdUseCase {

    suspend fun execute(customerId: String): CustomerDto
}