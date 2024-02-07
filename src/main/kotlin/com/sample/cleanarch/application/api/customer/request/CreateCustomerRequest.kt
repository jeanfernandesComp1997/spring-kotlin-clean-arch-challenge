package com.sample.cleanarch.application.api.customer.request

import com.sample.cleanarch.core.domain.dto.CreateCustomerInputDto
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class CreateCustomerRequest(
    val name: String,
    val document: String,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val birthDate: LocalDate,
    val email: String,
    val password: String
) {

    fun toCreateInputDto(): CreateCustomerInputDto {
        return CreateCustomerInputDto(
            name = this.document,
            document = this.document,
            birthDate = this.birthDate,
            email = this.email,
            password = this.password
        )
    }
}