package com.sample.cleanarch.core.usecase.impl

import com.sample.cleanarch.core.entity.valueobjects.UserType
import com.sample.cleanarch.core.exception.CustomerNotFoundException
import com.sample.cleanarch.core.model.response.CustomerResponseModel
import com.sample.cleanarch.core.port.CustomerPresenter
import com.sample.cleanarch.core.port.UserFindByIdDsGateway
import com.sample.cleanarch.core.usecase.FindCustomerByIdUseCase

class FindCustomerByIdUseCaseImpl(
    private val userDsGateway: UserFindByIdDsGateway,
    private val customerPresenter: CustomerPresenter
) : FindCustomerByIdUseCase {
    override suspend fun find(customerId: String): CustomerResponseModel {
        val customer = userDsGateway.findByIdAndType(customerId, UserType.COMMON_CUSTOMER.name)
        return customer?.let {
            customerPresenter.prepareSuccessView(
                CustomerResponseModel(
                    customer.id,
                    customer.name,
                    customer.document,
                    customer.birthDate,
                    customer.email,
                    customer.type,
                    customer.balance
                )
            )
        } ?: customerPresenter.prepareFailView(CustomerNotFoundException())
    }
}