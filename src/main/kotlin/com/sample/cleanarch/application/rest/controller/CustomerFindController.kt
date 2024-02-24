package com.sample.cleanarch.application.rest.controller

import com.sample.cleanarch.application.api.customer.CustomerFindApi
import com.sample.cleanarch.core.domain.dto.CustomerDto
import com.sample.cleanarch.core.usecase.FindCustomerByIdOnlyHaveNoRestrictionsUseCase
import com.sample.cleanarch.core.usecase.FindCustomerByIdUseCase
import com.sample.cleanarch.shared.log.annotation.Loggable
import org.springframework.web.bind.annotation.RestController

@RestController
class CustomerFindController(
    private val findCustomerByIdUseCase: FindCustomerByIdUseCase,
    private val findCustomerByIdOnlyHaveNoRestrictionsUseCase: FindCustomerByIdOnlyHaveNoRestrictionsUseCase
) : CustomerFindApi {

    @Loggable
    override suspend fun findById(id: String): CustomerDto {
        return findCustomerByIdUseCase.execute(id)
    }

    @Loggable
    override suspend fun findByIdOnlyHaveNoRestrictions(id: String): CustomerDto {
        return findCustomerByIdOnlyHaveNoRestrictionsUseCase.execute(id)
    }
}