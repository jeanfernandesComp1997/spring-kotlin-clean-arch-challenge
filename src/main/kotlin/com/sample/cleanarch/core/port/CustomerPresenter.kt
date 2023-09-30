package com.sample.cleanarch.core.port

import com.sample.cleanarch.core.model.response.CustomerResponseModel

interface CustomerPresenter {

    suspend fun prepareSuccessView(customerResponseModel: CustomerResponseModel): CustomerResponseModel

    suspend fun prepareFailView(error: Exception): CustomerResponseModel
}