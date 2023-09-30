package com.sample.cleanarch.adapter.service

import com.sample.cleanarch.core.model.request.EmailRequestServiceModel
import com.sample.cleanarch.core.port.SendEmailService
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class SendEmailServiceImpl(
    private val mailService: JavaMailSender
) : SendEmailService {

    override suspend fun send(emailRequestServiceModel: EmailRequestServiceModel) {
        mailService.send(generateEmail(emailRequestServiceModel))
    }

    private fun generateEmail(emailRequestServiceModel: EmailRequestServiceModel): SimpleMailMessage {
        return SimpleMailMessage()
            .apply {
                subject = emailRequestServiceModel.subject
                text = emailRequestServiceModel.body
                setTo(emailRequestServiceModel.to.email)
                from = emailRequestServiceModel.from.email
            }
    }
}