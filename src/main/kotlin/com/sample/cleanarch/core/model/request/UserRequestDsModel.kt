package com.sample.cleanarch.core.model.request

import java.time.LocalDate

data class UserRequestDsModel(
    val id: String,
    val name: String,
    val document: String,
    val birthDate: LocalDate,
    val email: String,
    val password: String,
    val balance: Double
)