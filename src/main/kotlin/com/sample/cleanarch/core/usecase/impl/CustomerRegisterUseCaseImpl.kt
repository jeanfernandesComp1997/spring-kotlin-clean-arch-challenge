package com.sample.cleanarch.core.usecase.impl

import com.sample.cleanarch.core.dto.CreateCustomerRequestDto
import com.sample.cleanarch.core.dto.CreateUserDataSourceDto
import com.sample.cleanarch.core.dto.CustomerDto
import com.sample.cleanarch.core.dto.EmailAddressRequestDto
import com.sample.cleanarch.core.dto.SendEmailRequestDto
import com.sample.cleanarch.core.entity.Customer
import com.sample.cleanarch.core.exception.DocumentRestrictionsException
import com.sample.cleanarch.core.exception.UserAlreadyExistsException
import com.sample.cleanarch.core.gateway.DocumentCheckerGateway
import com.sample.cleanarch.core.gateway.EmailGateway
import com.sample.cleanarch.core.gateway.UserRegisterDataSourceGateway
import com.sample.cleanarch.core.usecase.CustomerRegisterUseCase
import com.sample.cleanarch.shared.log.annotation.Loggable
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CustomerRegisterUseCaseImpl(
    private val userDsGateway: UserRegisterDataSourceGateway,
    private val emailGateway: EmailGateway,
    private val documentCheckerGateway: DocumentCheckerGateway
) : CustomerRegisterUseCase {

    val logger: Logger = LoggerFactory.getLogger(CustomerRegisterUseCaseImpl::class.java)

    companion object {

        private const val DEFAULT_GREETING_MESSAGE = "Welcome to our bank"
        private const val DEFAULT_BANK_EMAIL = "best-bank@anyone.com"
        private const val DEFAULT_BANK_NAME = "Best Bank"
        private const val DEFAULT_SUBJECT = "Best Bank Greetings!"
    }

    @Loggable
    override suspend fun execute(createCustomerRequest: CreateCustomerRequestDto): CustomerDto {
        logger.info("Start creating customer in thread: ${Thread.currentThread().name}")
        if (userDsGateway.existsByDocumentOrEmail(createCustomerRequest.document, createCustomerRequest.email)) {
            throw UserAlreadyExistsException()
        }
        val restrictions =
            documentCheckerGateway.retrieveDocumentRestrictions(createCustomerRequest.document)
        if (restrictions.isEmpty().not()) {
            throw DocumentRestrictionsException()
        }
        val customer = Customer(
            createCustomerRequest.name,
            createCustomerRequest.document,
            createCustomerRequest.birthDate,
            createCustomerRequest.email,
            createCustomerRequest.password
        )
        val createUserDatasource = CreateUserDataSourceDto(
            customer.id,
            customer.name,
            customer.document,
            customer.birthDate.value,
            customer.email.value,
            customer.type.name,
            customer.password.value,
            customer.balance
        )
        userDsGateway.save(createUserDatasource)
        val greetingsEmail = SendEmailRequestDto(
            EmailAddressRequestDto(customer.email.value, customer.name),
            EmailAddressRequestDto(DEFAULT_BANK_EMAIL, DEFAULT_BANK_NAME),
            DEFAULT_SUBJECT,
            "$DEFAULT_GREETING_MESSAGE ${customer.name}!"
        )
        emailGateway.send(greetingsEmail)
        return CustomerDto(
            customer.id,
            customer.name,
            customer.document,
            customer.birthDate.value,
            customer.email.value,
            customer.type.name,
            customer.balance
        )
    }
}