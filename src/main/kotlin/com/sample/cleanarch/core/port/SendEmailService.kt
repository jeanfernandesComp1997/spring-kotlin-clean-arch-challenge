package com.sample.cleanarch.core.port

import com.sample.cleanarch.core.model.request.EmailRequestServiceModel

interface SendEmailService {

    suspend fun send(emailRequestServiceModel: EmailRequestServiceModel)
}