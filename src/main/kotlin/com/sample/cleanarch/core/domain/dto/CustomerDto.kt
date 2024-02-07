package com.sample.cleanarch.core.domain.dto

import java.time.LocalDate

data class CustomerDto(
    val id: String,
    val name: String,
    val document: String,
    val birthDateTime: LocalDate,
    val email: String,
    val type: String,
    val balance: Double
)