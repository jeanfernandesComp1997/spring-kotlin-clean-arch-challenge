package com.sample.cleanarch.core.usecase

import com.sample.cleanarch.core.model.response.CustomerResponseModel

interface FindCustomerByIdUseCase {

    suspend fun find(customerId: String): CustomerResponseModel
}