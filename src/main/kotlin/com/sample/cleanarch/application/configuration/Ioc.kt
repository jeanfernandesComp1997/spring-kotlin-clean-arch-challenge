package com.sample.cleanarch.application.configuration

import com.sample.cleanarch.core.port.CustomerPresenter
import com.sample.cleanarch.core.port.SendEmailService
import com.sample.cleanarch.core.port.UserRegisterDsGateway
import com.sample.cleanarch.core.usecase.impl.CustomerRegisterUseCaseImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Ioc(
    private val userRegisterDsGateway: UserRegisterDsGateway,
    private val customerPresenter: CustomerPresenter,
    private val mailService: SendEmailService
) {

    @Bean
    fun customerRegisterUseCaseImpl(): CustomerRegisterUseCaseImpl {
        return CustomerRegisterUseCaseImpl(userRegisterDsGateway, mailService, customerPresenter)
    }
}