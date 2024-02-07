package com.sample.cleanarch.core.gateway

import com.sample.cleanarch.core.domain.dto.SendEmailRequestDto

interface EmailGateway {

    suspend fun send(sendEmailRequest: SendEmailRequestDto)
}