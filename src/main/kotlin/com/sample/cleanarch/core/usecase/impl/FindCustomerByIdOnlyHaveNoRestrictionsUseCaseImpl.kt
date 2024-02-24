package com.sample.cleanarch.core.usecase.impl

import com.sample.cleanarch.core.domain.dto.CustomerDto
import com.sample.cleanarch.core.domain.entity.valueobjects.UserType
import com.sample.cleanarch.core.domain.exception.CustomerNotFoundException
import com.sample.cleanarch.core.domain.exception.DocumentRestrictionsException
import com.sample.cleanarch.core.gateway.DocumentCheckerGateway
import com.sample.cleanarch.core.gateway.UserFindByIdDataSourceGateway
import com.sample.cleanarch.core.usecase.FindCustomerByIdOnlyHaveNoRestrictionsUseCase
import com.sample.cleanarch.shared.log.annotation.Loggable
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FindCustomerByIdOnlyHaveNoRestrictionsUseCaseImpl(
    private val userDsGateway: UserFindByIdDataSourceGateway,
    private val documentCheckerGateway: DocumentCheckerGateway
) : FindCustomerByIdOnlyHaveNoRestrictionsUseCase {

    private val logger: Logger = LoggerFactory.getLogger(FindCustomerByIdUseCaseImpl::class.java)

    @Loggable
    override suspend fun execute(customerId: String): CustomerDto {
        logger.info("Searching customer in thread: ${Thread.currentThread().name}")
        val customer = userDsGateway.findByIdAndType(customerId, UserType.COMMON_CUSTOMER.name)?.let { customer ->
            CustomerDto(
                customer.id,
                customer.name,
                customer.document,
                customer.birthDate,
                customer.email,
                customer.type,
                customer.balance
            )
        } ?: throw CustomerNotFoundException()
        val restrictions =
            documentCheckerGateway.retrieveDocumentRestrictions(customer.document)
        if (restrictions.isEmpty().not()) {
            throw DocumentRestrictionsException()
        }
        return customer
    }
}