package com.sample.cleanarch.core.domain.exception

class CustomerNotFoundException : RuntimeException(
    "Customer not found!"
)