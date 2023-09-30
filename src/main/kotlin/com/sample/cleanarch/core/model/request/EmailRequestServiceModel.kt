package com.sample.cleanarch.core.model.request


data class EmailRequestServiceModel(
    val to: EmailAddressRequestServiceModel,
    val from: EmailAddressRequestServiceModel,
    val subject: String,
    val body: String
)

data class EmailAddressRequestServiceModel(
    val email: String,
    val name: String
)