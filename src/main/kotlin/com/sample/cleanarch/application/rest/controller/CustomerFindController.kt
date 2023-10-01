package com.sample.cleanarch.application.rest.controller

import com.sample.cleanarch.core.model.response.CustomerResponseModel
import com.sample.cleanarch.core.usecase.FindCustomerByIdUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("customers")
class CustomerFindController(
    private val customerFindCustomerByIdUseCase: FindCustomerByIdUseCase
) {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun findById(@PathVariable("id") id: String): CustomerResponseModel {
        return customerFindCustomerByIdUseCase.find(id)
    }
}