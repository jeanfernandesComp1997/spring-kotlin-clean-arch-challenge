package com.sample.cleanarch.core.domain.exception

class UserAlreadyExistsException(
) : RuntimeException(
    "User already exists!"
)