package com.sample.cleanarch.application.rest.controller

import com.sample.cleanarch.core.command.CreateCustomerCommand
import com.sample.cleanarch.core.model.response.CustomerResponseModel
import com.sample.cleanarch.core.usecase.CustomerRegisterUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("customers")
class CustomerRegisterController(
    private val customerRegisterUseCase: CustomerRegisterUseCase
) {

    @PostMapping
    suspend fun register(@RequestBody command: CreateCustomerCommand): ResponseEntity<CustomerResponseModel> {
        val customer = customerRegisterUseCase.create(command)
        return ResponseEntity
            .created(URI.create("/customers/${customer.id}"))
            .body(customer)
    }
}