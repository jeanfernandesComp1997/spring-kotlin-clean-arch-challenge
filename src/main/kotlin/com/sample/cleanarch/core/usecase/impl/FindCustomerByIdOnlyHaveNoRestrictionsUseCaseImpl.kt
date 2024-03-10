package com.sample.cleanarch.core.usecase.impl

import com.sample.cleanarch.core.domain.dto.CustomerDto
import com.sample.cleanarch.core.domain.dto.FindCustomerByIdRequestContext
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
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.switchIfEmpty

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

//        val restrictions = Mono.fromCallable {
//            documentCheckerGateway.retrieveDocumentRestrictionsSimulatingBlockingLibrary(customer.document)
//        }
//            .subscribeOn(Schedulers.boundedElastic())
//            .awaitSingle()

        if (restrictions.isEmpty().not()) {
            throw DocumentRestrictionsException()
        }
        return customer
    }

    override fun executeFlux(customerId: Mono<String>): Mono<CustomerDto> {
        logger.info("Searching customer in thread: ${Thread.currentThread().name}")
        return customerId
            .map { id ->
                FindCustomerByIdRequestContext(id)
            }
            .flatMap { requestContext ->
                retrieveCustomer(requestContext)
            }
            .flatMap { requestContext ->
                retrieveRestrictions(requestContext)
            }
            .doOnNext { requestContext ->
                if (requestContext.restrictions!!.isEmpty().not()) {
                    Mono.error<DocumentRestrictionsException>(DocumentRestrictionsException())
                }
            }
            .map { requestContext ->
                logger.info("Final map executeFlux: ${Thread.currentThread().name}")
                CustomerDto(
                    requestContext.customer!!.id,
                    requestContext.customer!!.name,
                    requestContext.customer!!.document,
                    requestContext.customer!!.birthDateTime,
                    requestContext.customer!!.email,
                    requestContext.customer!!.type,
                    requestContext.customer!!.balance
                )
            }
    }

    @Loggable
    private fun retrieveCustomer(requestContext: FindCustomerByIdRequestContext): Mono<FindCustomerByIdRequestContext> {
        return userDsGateway
            .findByIdAndTypeFlux(requestContext.customerRequestId, UserType.COMMON_CUSTOMER.name)
            .switchIfEmpty { Mono.error(CustomerNotFoundException()) }
            .map { customer ->
                CustomerDto(
                    customer.id,
                    customer.name,
                    customer.document,
                    customer.birthDate,
                    customer.email,
                    customer.type,
                    customer.balance
                )
            }
            .doOnNext { customer ->
                requestContext.customer = customer
            }
            .thenReturn(requestContext)
    }

    private fun retrieveRestrictions(requestContext: FindCustomerByIdRequestContext): Mono<FindCustomerByIdRequestContext> {
        return documentCheckerGateway
            .retrieveDocumentRestrictionsFlux(requestContext.customerRequestId)
            .switchIfEmpty { Mono.error(CustomerNotFoundException()) }
            .doOnNext { restrictions ->
                requestContext.restrictions = restrictions
            }
            .thenReturn(requestContext)
    }
}