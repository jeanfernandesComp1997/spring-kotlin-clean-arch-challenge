package com.sample.cleanarch.gateway.email

import com.sample.cleanarch.core.dto.SendEmailRequestDto
import com.sample.cleanarch.core.gateway.EmailGateway
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailGatewayImpl(
    private val mailService: JavaMailSender
) : EmailGateway {

    val logger: Logger = LoggerFactory.getLogger(EmailGatewayImpl::class.java)

    override suspend fun send(sendEmailRequest: SendEmailRequestDto) = withContext(Dispatchers.IO) {
        logger.info("Sending email in thread: ${Thread.currentThread().name}")
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