package com.sample.cleanarch.core.domain.dto

class FindCustomerByIdRequestContext(val customerRequestId: String) {
    var customer: CustomerDto? = null
    var restrictions: List<String>? = null
}