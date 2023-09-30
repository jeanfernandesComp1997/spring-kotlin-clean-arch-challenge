package com.sample.cleanarch.core.command

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class CreateCustomerCommand(
    val name: String,
    val document: String,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val birthDate: LocalDate,
    val email: String,
    val password: String
)