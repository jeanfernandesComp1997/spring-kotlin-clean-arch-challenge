package com.sample.cleanarch.core.usecase

import com.sample.cleanarch.core.dto.CreateCustomerRequestDto
import com.sample.cleanarch.core.dto.CustomerDto

interface CustomerRegisterUseCase {

    suspend fun execute(createCustomerRequest: CreateCustomerRequestDto): CustomerDto
}