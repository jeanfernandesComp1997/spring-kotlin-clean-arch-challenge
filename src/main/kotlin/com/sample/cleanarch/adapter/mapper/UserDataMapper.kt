package com.sample.cleanarch.adapter.mapper

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table(name = "users")
data class UserDataMapper(
    val id: String,
    val name: String,
    val document: String,
    @Column("birth_date")
    val birthDate: LocalDate,
    val email: String,
    val password: String,
    val balance: Double
)