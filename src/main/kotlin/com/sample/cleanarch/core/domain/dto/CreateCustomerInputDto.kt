package com.sample.cleanarch.core.domain.dto

import java.time.LocalDate

data class CreateCustomerInputDto(
    val name: String,
    val document: String,
    val birthDate: LocalDate,
    val email: String,
    val password: String
)