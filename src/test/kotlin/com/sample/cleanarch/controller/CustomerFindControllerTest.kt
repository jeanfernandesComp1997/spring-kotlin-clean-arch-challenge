package com.sample.cleanarch.controller

import com.sample.cleanarch.application.rest.controller.CustomerFindController
import com.sample.cleanarch.core.domain.dto.UserDataSourceDto
import com.sample.cleanarch.core.gateway.UserFindByIdDataSourceGateway
import com.sample.cleanarch.core.usecase.FindCustomerByIdOnlyHaveNoRestrictionsUseCase
import com.sample.cleanarch.core.usecase.impl.FindCustomerByIdUseCaseImpl
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDate

@WebFluxTest(controllers = [CustomerFindController::class])
@ExtendWith(SpringExtension::class)
@Import(FindCustomerByIdUseCaseImpl::class)
class CustomerFindControllerTest {

    @MockBean
    private lateinit var userFindByIdDataSourceGateway: UserFindByIdDataSourceGateway

    @MockBean
    private lateinit var findCustomerByIdOnlyHaveNoRestrictionsUseCase: FindCustomerByIdOnlyHaveNoRestrictionsUseCase

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun `should return a customer by id with success`() = runTest {
        // Given
        val customer = UserDataSourceDto(
            id = "ccfaad30-716e-4e0b-885a-eecb04d94080",
            name = "John Doe",
            document = "11111111111",
            birthDate = LocalDate.parse("1997-04-30"),
            email = "johndoe1@email.com",
            type = "COMMON_CUSTOMER",
            balance = 0.00
        )

        Mockito.`when`(userFindByIdDataSourceGateway.findByIdAndType(anyString(), anyString()))
            .thenReturn(customer)

        // When Then
        webTestClient
            .get()
            .uri("/customers/{id}", "ccfaad30-716e-4e0b-885a-eecb04d94080")
            .exchange()
            .expectStatus().isOk
    }
}