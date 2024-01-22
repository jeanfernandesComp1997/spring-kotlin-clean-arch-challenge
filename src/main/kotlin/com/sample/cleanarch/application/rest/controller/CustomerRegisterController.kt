package com.sample.cleanarch.application.rest.controller

import com.sample.cleanarch.application.api.customer.CustomerRegisterApi
import com.sample.cleanarch.core.dto.CreateCustomerRequestDto
import com.sample.cleanarch.core.dto.CustomerDto
import com.sample.cleanarch.core.usecase.CustomerRegisterUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class CustomerRegisterController(
    private val customerRegisterUseCase: CustomerRegisterUseCase
) : CustomerRegisterApi {

    override suspend fun register(createCustomerRequest: CreateCustomerRequestDto): ResponseEntity<CustomerDto> {
        val customer = customerRegisterUseCase.execute(createCustomerRequest)
        return ResponseEntity
            .created(URI.create("/customers/${customer.id}"))
            .body(customer)
    }
}