package com.sample.cleanarch.core.usecase.impl

import com.sample.cleanarch.core.domain.dto.CustomerDto
import com.sample.cleanarch.core.domain.entity.valueobjects.UserType
import com.sample.cleanarch.core.domain.exception.CustomerNotFoundException
import com.sample.cleanarch.core.gateway.UserFindByIdDataSourceGateway
import com.sample.cleanarch.core.usecase.FindCustomerByIdUseCase
import com.sample.cleanarch.shared.log.annotation.Loggable
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FindCustomerByIdUseCaseImpl(
    private val userDsGateway: UserFindByIdDataSourceGateway
) : FindCustomerByIdUseCase {

    val logger: Logger = LoggerFactory.getLogger(FindCustomerByIdUseCaseImpl::class.java)

    @Loggable
    override suspend fun execute(customerId: String): CustomerDto {
        logger.info("Searching customer in thread: ${Thread.currentThread().name}")
        return userDsGateway.findByIdAndType(customerId, UserType.COMMON_CUSTOMER.name)?.let { customer ->
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
    }
}