package com.sample.cleanarch.core.usecase

import com.sample.cleanarch.core.command.CreateCustomerCommand
import com.sample.cleanarch.core.model.response.CustomerResponseModel

interface CustomerRegisterUseCase {

    suspend fun create(command: CreateCustomerCommand): CustomerResponseModel
}