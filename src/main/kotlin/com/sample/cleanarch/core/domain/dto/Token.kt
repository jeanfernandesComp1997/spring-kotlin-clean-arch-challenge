package com.sample.cleanarch.core.domain.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class Token(
    @JsonProperty("access_token")
    val accessToken: String,
    @JsonProperty("refresh_token")
    val refreshToken: String
)