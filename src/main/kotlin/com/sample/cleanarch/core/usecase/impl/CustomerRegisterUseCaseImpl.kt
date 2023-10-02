package com.sample.cleanarch.core.usecase.impl

import com.sample.cleanarch.core.command.CreateCustomerCommand
import com.sample.cleanarch.core.entity.Customer
import com.sample.cleanarch.core.exception.UserAlreadyExistsException
import com.sample.cleanarch.core.model.request.EmailAddressRequestServiceModel
import com.sample.cleanarch.core.model.request.EmailRequestServiceModel
import com.sample.cleanarch.core.model.request.UserRequestDsModel
import com.sample.cleanarch.core.model.response.CustomerResponseModel
import com.sample.cleanarch.core.port.CustomerPresenter
import com.sample.cleanarch.core.port.SendEmailService
import com.sample.cleanarch.core.port.UserRegisterDsGateway
import com.sample.cleanarch.core.usecase.CustomerRegisterUseCase

class CustomerRegisterUseCaseImpl(
    private val userDsGateway: UserRegisterDsGateway,
    private val emailService: SendEmailService,
    private val customerPresenter: CustomerPresenter
) : CustomerRegisterUseCase {

    companion object {

        private const val DEFAULT_GREETING_MESSAGE = "Welcome to our bank"
        private const val DEFAULT_BANK_EMAIL = "best-bank@anyone.com"
        private const val DEFAULT_BANK_NAME = "Best Bank"
        private const val DEFAULT_SUBJECT = "Best Bank Greetings!"
    }

    override suspend fun create(command: CreateCustomerCommand): CustomerResponseModel {
        if (userDsGateway.existsByDocumentOrEmail(command.document, command.email)) {
            return customerPresenter.prepareFailView(UserAlreadyExistsException())
        }
        val customer = Customer(
            command.name,
            command.document,
            command.birthDate,
            command.email,
            command.password
        )
        val userDsModel = UserRequestDsModel(
            customer.id,
            customer.name,
            customer.document,
            customer.birthDate.value,
            customer.email.value,
            customer.type.name,
            customer.password.value,
            customer.balance
        )
        userDsGateway.save(userDsModel)
        val greetingsEmail = EmailRequestServiceModel(
            EmailAddressRequestServiceModel(customer.email.value, customer.name),
            EmailAddressRequestServiceModel(DEFAULT_BANK_EMAIL, DEFAULT_BANK_NAME),
            DEFAULT_SUBJECT,
            "$DEFAULT_GREETING_MESSAGE ${customer.name}!"
        )
        emailService.send(greetingsEmail)
        val customerResponseModel = CustomerResponseModel(
            customer.id,
            customer.name,
            customer.document,
            customer.birthDate.value,
            customer.email.value,
            customer.type.name,
            customer.balance
        )
        return customerPresenter.prepareSuccessView(customerResponseModel)
    }
}