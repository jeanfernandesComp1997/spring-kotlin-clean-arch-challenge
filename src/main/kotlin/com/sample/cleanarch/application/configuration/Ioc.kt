package com.sample.cleanarch.application.configuration

import com.sample.cleanarch.core.port.CustomerPresenter
import com.sample.cleanarch.core.port.SendEmailService
import com.sample.cleanarch.core.port.UserFindByIdDsGateway
import com.sample.cleanarch.core.port.UserRegisterDsGateway
import com.sample.cleanarch.core.usecase.CustomerRegisterUseCase
import com.sample.cleanarch.core.usecase.FindCustomerByIdUseCase
import com.sample.cleanarch.core.usecase.impl.CustomerRegisterUseCaseImpl
import com.sample.cleanarch.core.usecase.impl.FindCustomerByIdUseCaseImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class Ioc(
    private val userRegisterDsGateway: UserRegisterDsGateway,
    private val customerPresenter: CustomerPresenter,
    private val mailService: SendEmailService,
    private val userFindByIdDsGateway: UserFindByIdDsGateway
) {

    @Bean
    fun customerRegisterUseCase(): CustomerRegisterUseCase {
        return CustomerRegisterUseCaseImpl(userRegisterDsGateway, mailService, customerPresenter)
    }

    @Bean
    fun findCustomerByIdUseCase(): FindCustomerByIdUseCase {
        return FindCustomerByIdUseCaseImpl(userFindByIdDsGateway, customerPresenter)
    }
}