package com.sample.cleanarch.gateway.dataprovider

import com.sample.cleanarch.core.dto.CreateUserDataSourceDto
import com.sample.cleanarch.shared.mapper.UserDataMapper
import com.sample.cleanarch.core.dto.UserDataSourceDto
import com.sample.cleanarch.core.gateway.UserFindByIdDataSourceGateway
import com.sample.cleanarch.core.gateway.UserRegisterDataSourceGateway
import com.sample.cleanarch.shared.log.annotation.Loggable
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component

@Component
class R2dbcUser(
    private val repository: R2dbcUserRepository
) : UserRegisterDataSourceGateway, UserFindByIdDataSourceGateway {

    @Loggable
    override suspend fun existsByDocumentOrEmail(document: String, email: String): Boolean {
        return repository
            .existsByDocumentOrEmail(document, email)
    }

    @Loggable
    override suspend fun save(createUserDataSource: CreateUserDataSourceDto) {
        val userDataMapped = UserDataMapper(
            createUserDataSource.id,
            createUserDataSource.name,
            createUserDataSource.document,
            createUserDataSource.birthDate,
            createUserDataSource.email,
            createUserDataSource.type,
            createUserDataSource.password,
            createUserDataSource.balance
        )
        repository.save(userDataMapped).awaitSingle()
    }

    @Loggable
    override suspend fun findByIdAndType(id: String, type: String): UserDataSourceDto? {
        return repository
            .findByIdAndType(id, type)
            ?.let { userDataMapper ->
                UserDataSourceDto(
                    userDataMapper.id,
                    userDataMapper.name,
                    userDataMapper.document,
                    userDataMapper.birthDate,
                    userDataMapper.email,
                    userDataMapper.type,
                    userDataMapper.balance
                )
            }
    }
}