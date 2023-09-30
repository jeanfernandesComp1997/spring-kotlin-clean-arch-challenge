package com.sample.cleanarch.core.entity

import java.time.LocalDate

class ShopKeeper : User {

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