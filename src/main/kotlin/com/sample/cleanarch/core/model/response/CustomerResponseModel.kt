package com.sample.cleanarch.core.model.response

import java.time.LocalDate

data class CustomerResponseModel(
    val id: String,
    val name: String,
    val document: String,
    val birthDateTime: LocalDate,
    val email: String,
    val balance: Double
)