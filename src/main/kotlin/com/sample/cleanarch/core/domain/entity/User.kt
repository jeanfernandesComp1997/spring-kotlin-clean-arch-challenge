package com.sample.cleanarch.core.domain.entity

import com.sample.cleanarch.core.domain.entity.valueobjects.BirthDate
import com.sample.cleanarch.core.domain.entity.valueobjects.Email
import com.sample.cleanarch.core.domain.entity.valueobjects.Password
import java.time.LocalDate
import java.util.UUID

abstract class User {

    val id: String

    var name: String
        private set

    var document: String
        private set

    var birthDate: BirthDate
        private set

    var email: Email
        private set

    var password: Password
        private set

    var balance: Double
        private set

    constructor(
        id: String,
        name: String,
        document: String,
        birthDate: LocalDate,
        email: String,
        password: String,
        balance: Double
    ) {
        this.id = id
        this.name = name
        this.document = document
        this.birthDate = BirthDate(birthDate)
        this.email = Email(email)
        this.password = Password(password)
        this.balance = balance
    }

    constructor(
        name: String,
        document: String,
        birthDate: LocalDate,
        email: String,
        password: String,
    ) {
        this.id = UUID.randomUUID().toString()
        this.name = name
        this.document = document
        this.birthDate = BirthDate(birthDate).apply { validate() }
        this.email = Email(email).apply { validate() }
        this.password = Password(password).apply { validate() }
        this.balance = 0.0
    }
}