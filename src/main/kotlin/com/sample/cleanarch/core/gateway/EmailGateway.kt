package com.sample.cleanarch.core.gateway

import com.sample.cleanarch.core.dto.SendEmailRequestDto

interface EmailGateway {

    suspend fun send(sendEmailRequest: SendEmailRequestDto)
}