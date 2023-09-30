package com.sample.cleanarch.adapter.formatter

import com.sample.cleanarch.core.model.response.CustomerResponseModel
import com.sample.cleanarch.core.port.CustomerPresenter
import org.springframework.stereotype.Component

@Component
class CustomerResponseFormatter : CustomerPresenter {

    override suspend fun prepareSuccessView(customerResponseModel: CustomerResponseModel): CustomerResponseModel {
        return customerResponseModel
    }

    override suspend fun prepareFailView(error: Exception): CustomerResponseModel {
        throw error
    }
}