package com.sample.cleanarch.adapter.dataprovider

import com.sample.cleanarch.adapter.mapper.UserDataMapper
import com.sample.cleanarch.core.model.request.UserRequestDsModel
import com.sample.cleanarch.core.port.UserRegisterDsGateway
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component

@Component
class R2dbcUser(
    private val repository: R2dbcUserRepository
) : UserRegisterDsGateway {

    override suspend fun existsByDocumentOrEmail(document: String, email: String): Boolean {
        return repository
            .existsByDocumentOrEmail(document, email)
            .awaitSingle()
    }

    override suspend fun save(userRequestDsModel: UserRequestDsModel) {
        val userDataMapped = UserDataMapper(
            userRequestDsModel.id,
            userRequestDsModel.name,
            userRequestDsModel.document,
            userRequestDsModel.birthDate,
            userRequestDsModel.email,
            userRequestDsModel.password,
            userRequestDsModel.balance
        )

        repository.save(userDataMapped).awaitSingle()
    }
}