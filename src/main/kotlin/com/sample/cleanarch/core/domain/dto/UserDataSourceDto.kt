package com.sample.cleanarch.core.domain.dto

import java.time.LocalDate

class UserDataSourceDto(
    val id: String,
    val name: String,
    val document: String,
    val birthDate: LocalDate,
    val email: String,
    val type: String,
    val balance: Double
)