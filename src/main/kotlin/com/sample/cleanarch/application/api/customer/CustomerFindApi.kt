package com.sample.cleanarch.application.api.customer

import com.sample.cleanarch.core.domain.annotation.ValidUUID
import com.sample.cleanarch.core.domain.dto.CustomerDto
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@RequestMapping("customers")
@Validated
interface CustomerFindApi {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun findById(@PathVariable("id") @Valid @ValidUUID id: String): CustomerDto
}