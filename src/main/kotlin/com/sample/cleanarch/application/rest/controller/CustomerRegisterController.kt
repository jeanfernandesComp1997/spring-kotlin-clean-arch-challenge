package com.sample.cleanarch.application.rest.controller

import com.sample.cleanarch.application.api.customer.CustomerRegisterApi
import com.sample.cleanarch.application.api.customer.request.CreateCustomerRequest
import com.sample.cleanarch.core.domain.dto.CustomerDto
import com.sample.cleanarch.core.usecase.CustomerRegisterUseCase
import com.sample.cleanarch.shared.log.annotation.Loggable
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class CustomerRegisterController(
    private val customerRegisterUseCase: CustomerRegisterUseCase,
) : CustomerRegisterApi {

    val logger: Logger = LoggerFactory.getLogger(CustomerRegisterController::class.java)

    @Loggable
    override suspend fun register(createCustomerRequest: CreateCustomerRequest): ResponseEntity<CustomerDto> {
        val createCustomerInput = createCustomerRequest.toCreateInputDto()
        val customer = customerRegisterUseCase.execute(createCustomerInput)
        logger.info("Finish register customer, thread: ${Thread.currentThread().name}")
        return ResponseEntity
            .created(URI.create("/customers/${customer.id}"))
            .body(customer)
    }
}