package com.sample.cleanarch.core.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class CreateCustomerRequestDto(
    val name: String,
    val document: String,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val birthDate: LocalDate,
    val email: String,
    val password: String
)