package com.sample.cleanarch.core.dto


data class SendEmailRequestDto(
    val to: EmailAddressRequestDto,
    val from: EmailAddressRequestDto,
    val subject: String,
    val body: String
)

data class EmailAddressRequestDto(
    val email: String,
    val name: String
)