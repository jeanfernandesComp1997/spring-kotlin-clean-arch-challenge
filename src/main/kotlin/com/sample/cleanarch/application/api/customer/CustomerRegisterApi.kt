package com.sample.cleanarch.application.api.customer

import com.sample.cleanarch.core.domain.dto.CreateCustomerRequestDto
import com.sample.cleanarch.core.domain.dto.CustomerDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("customers")
interface CustomerRegisterApi {

    @PostMapping
    suspend fun register(@RequestBody createCustomerRequest: CreateCustomerRequestDto): ResponseEntity<CustomerDto>
}