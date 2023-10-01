package com.sample.cleanarch.core.model.response

import java.time.LocalDate

class UserResponseDsModel(
    val id: String,
    val name: String,
    val document: String,
    val birthDate: LocalDate,
    val email: String,
    val type: String,
    val balance: Double
)