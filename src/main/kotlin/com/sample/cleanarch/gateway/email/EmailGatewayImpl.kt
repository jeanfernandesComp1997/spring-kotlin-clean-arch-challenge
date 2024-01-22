package com.sample.cleanarch.gateway.email

import com.sample.cleanarch.core.dto.SendEmailRequestDto
import com.sample.cleanarch.core.gateway.EmailGateway
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailGatewayImpl(
    private val mailService: JavaMailSender
) : EmailGateway {

    override suspend fun send(sendEmailRequest: SendEmailRequestDto) {
        mailService.send(generateEmail(sendEmailRequest))
    }

    private fun generateEmail(emailRequestServiceModel: SendEmailRequestDto): SimpleMailMessage {
        return SimpleMailMessage()
            .apply {
                subject = emailRequestServiceModel.subject
                text = emailRequestServiceModel.body
                setTo(emailRequestServiceModel.to.email)
                from = emailRequestServiceModel.from.email
            }
    }
}