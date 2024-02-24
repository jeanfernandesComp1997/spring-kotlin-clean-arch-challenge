package com.sample.cleanarch.core.usecase

import com.sample.cleanarch.core.domain.dto.CustomerDto

interface FindCustomerByIdOnlyHaveNoRestrictionsUseCase {

    suspend fun execute(customerId: String): CustomerDto
}