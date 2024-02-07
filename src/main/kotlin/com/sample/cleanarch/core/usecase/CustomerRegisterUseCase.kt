package com.sample.cleanarch.core.usecase

import com.sample.cleanarch.core.domain.dto.CreateCustomerRequestDto
import com.sample.cleanarch.core.domain.dto.CustomerDto
import com.sample.cleanarch.shared.log.annotation.Loggable

interface CustomerRegisterUseCase {

    @Loggable
    suspend fun execute(createCustomerRequest: CreateCustomerRequestDto): CustomerDto
}