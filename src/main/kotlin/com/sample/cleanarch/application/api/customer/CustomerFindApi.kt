package com.sample.cleanarch.application.api.customer

import com.sample.cleanarch.core.dto.CustomerDto
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@RequestMapping("customers")
interface CustomerFindApi {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun findById(@PathVariable("id") id: String): CustomerDto
}