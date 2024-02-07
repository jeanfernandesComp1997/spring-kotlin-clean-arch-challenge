package com.sample.cleanarch.core.domain.entity

import com.sample.cleanarch.core.domain.entity.valueobjects.UserType
import java.time.LocalDate

class Customer : User {

    val type = UserType.COMMON_CUSTOMER

    constructor(
        name: String,
        document: String,
        birthDateTime: LocalDate,
        email: String,
        password: String
    ) : super(name, document, birthDateTime, email, password)

    constructor(
        id: String,
        name: String,
        document: String,
        birthDateTime: LocalDate,
        email: String,
        password: String,
        balance: Double
    ) : super(id, name, document, birthDateTime, email, password, balance)
}