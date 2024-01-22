package com.sample.cleanarch.core.usecase.impl

import com.sample.cleanarch.core.dto.CustomerDto
import com.sample.cleanarch.core.entity.valueobjects.UserType
import com.sample.cleanarch.core.exception.CustomerNotFoundException
import com.sample.cleanarch.core.gateway.UserFindByIdDataSourceGateway
import com.sample.cleanarch.core.usecase.FindCustomerByIdUseCase
import org.springframework.stereotype.Service

@Service
class FindCustomerByIdUseCaseImpl(
    private val userDsGateway: UserFindByIdDataSourceGateway
) : FindCustomerByIdUseCase {
    override suspend fun execute(customerId: String): CustomerDto {
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