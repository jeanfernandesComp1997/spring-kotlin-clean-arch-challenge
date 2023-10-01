package com.sample.cleanarch.adapter.dataprovider

import com.sample.cleanarch.adapter.mapper.UserDataMapper
import com.sample.cleanarch.core.model.request.UserRequestDsModel
import com.sample.cleanarch.core.model.response.UserResponseDsModel
import com.sample.cleanarch.core.port.UserFindByIdDsGateway
import com.sample.cleanarch.core.port.UserRegisterDsGateway
import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class R2dbcUser(
    private val repository: R2dbcUserRepository
) : UserRegisterDsGateway, UserFindByIdDsGateway {

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
            userRequestDsModel.type,
            userRequestDsModel.password,
            userRequestDsModel.balance
        )

        repository.save(userDataMapped).awaitSingle()
    }

    override suspend fun findByIdAndType(id: String, type: String): UserResponseDsModel? {
        return repository
            .findByIdAndType(id, type)
            .map { userDataMapper ->
                UserResponseDsModel(
                    userDataMapper.id,
                    userDataMapper.name,
                    userDataMapper.document,
                    userDataMapper.birthDate,
                    userDataMapper.email,
                    userDataMapper.type,
                    userDataMapper.balance
                )
            }
            .awaitSingleOrNull()
    }
}