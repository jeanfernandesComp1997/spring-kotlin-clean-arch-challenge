package com.sample.cleanarch.shared.log

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Aspect
@Component
class LoggingAspect {

    @Around("@annotation(com.sample.cleanarch.shared.log.annotation.Loggable)")
    fun logInOut(joinPoint: ProceedingJoinPoint): Any? {
        val clazz: Class<*> = joinPoint.target.javaClass
        val logger: Logger = LoggerFactory.getLogger(clazz)

        val start = System.currentTimeMillis()
        var result: Any? = null
        var exception: Throwable? = null
        try {
            result = joinPoint.proceed()
            return when (result) {
                is Mono<*> -> logMono(result, logger, joinPoint, start)
                is Flux<*> -> logFlux(result, logger, joinPoint, start)
                else -> logResult(result, logger, joinPoint, start)
            }
        } catch (e: Throwable) {
            exception = e
            throw e
        } finally {
            if (result !is Mono<*> && result !is Flux<*>) {
                logger.error(
                    "Enter: {}.{}() with argument[s] = {}",
                    joinPoint.signature.declaringTypeName, joinPoint.signature.name,
                    joinPoint.args
                )
                logger.error(
                    "Exit: {}.{}() had arguments = {}, with error = {}, Execution time = {} ms",
                    joinPoint.signature.declaringTypeName, joinPoint.signature.name,
                    joinPoint.args[0],
                    exception?.message, (System.currentTimeMillis() - start)
                )
            }
        }
    }

    private fun <T> logMono(mono: Mono<T>, logger: Logger, joinPoint: ProceedingJoinPoint, start: Long): Mono<T> {
        return mono
            .switchIfEmpty(Mono.empty<T>()
                .doOnSuccess {
                    logger.info(
                        "Enter: {}.{}() with argument[s] = {}",
                        joinPoint.signature.declaringTypeName, joinPoint.signature.name,
                        joinPoint.args
                    )
                    logger.warn(
                        "Exit: {}.{}(), with result = {}, Execution time = {} ms",
                        joinPoint.signature.declaringTypeName, joinPoint.signature.name,
                        "[empty]", (System.currentTimeMillis() - start)
                    )
                })
            .doOnSuccess { monoOutput: T ->
                val response = if (monoOutput !== null) monoOutput.toString() else ""
                logger.info(
                    "Enter: {}.{}() with argument[s] = {}",
                    joinPoint.signature.declaringTypeName, joinPoint.signature.name,
                    joinPoint.args
                )
                logger.info(
                    "Exit: {}.{}(), with result = {}, Execution time = {} ms",
                    joinPoint.signature.declaringTypeName, joinPoint.signature.name,
                    response, (System.currentTimeMillis() - start)
                )
            }
            .doOnError { exception ->
                logger.error(
                    "Enter: {}.{}() with argument[s] = {}",
                    joinPoint.signature.declaringTypeName, joinPoint.signature.name,
                    joinPoint.args
                )
                logger.error(
                    "Exit: {}.{}(), with error = {}, Execution time = {} ms",
                    joinPoint.signature.declaringTypeName, joinPoint.signature.name,
                    exception.message, (System.currentTimeMillis() - start)
                )
            }
            .doOnCancel {
                logger.info(
                    "Enter: {}.{}() with argument[s] = {}",
                    joinPoint.signature.declaringTypeName, joinPoint.signature.name,
                    joinPoint.args
                )
                logger.warn(
                    "Exit: {}.{}(), with result = {}, Execution time = {} ms",
                    joinPoint.signature.declaringTypeName, joinPoint.signature.name,
                    "[cancelled]", (System.currentTimeMillis() - start)
                )
            }
    }

    private fun <T> logFlux(flux: Flux<T>, logger: Logger, joinPoint: ProceedingJoinPoint, start: Long): Flux<T> {
        return flux
            .switchIfEmpty(Flux.empty<T>()
                .doOnComplete {
                    logger.info(
                        "Enter: {}.{}() with argument[s] = {}",
                        joinPoint.signature.declaringTypeName, joinPoint.signature.name,
                        joinPoint.args
                    )
                    logger.warn(
                        "Exit: {}.{}() had arguments = {}, with result = {}, Execution time = {} ms",
                        joinPoint.signature.declaringTypeName, joinPoint.signature.name,
                        joinPoint.args[0],
                        "[empty]", (System.currentTimeMillis() - start)
                    )
                })
            .doOnEach {
                val response = if (it !== null) it.toString() else ""
                logger.info(
                    "Enter: {}.{}() with argument[s] = {}",
                    joinPoint.signature.declaringTypeName, joinPoint.signature.name,
                    joinPoint.args
                )
                logger.info(
                    "Exit: {}.{}() had arguments = {}, with result = {}, Execution time = {} ms",
                    joinPoint.signature.declaringTypeName, joinPoint.signature.name,
                    joinPoint.args[0],
                    response, (System.currentTimeMillis() - start)
                )
            }
            .doOnError { exception ->
                logger.error(
                    "Enter: {}.{}() with argument[s] = {}",
                    joinPoint.signature.declaringTypeName, joinPoint.signature.name,
                    joinPoint.args
                )
                logger.error(
                    "Exit: {}.{}() had arguments = {}, with error = {}, Execution time = {} ms",
                    joinPoint.signature.declaringTypeName, joinPoint.signature.name,
                    joinPoint.args[0],
                    exception.message, (System.currentTimeMillis() - start)
                )
            }
            .doOnCancel {
                logger.info(
                    "Enter: {}.{}() with argument[s] = {}",
                    joinPoint.signature.declaringTypeName, joinPoint.signature.name,
                    joinPoint.args
                )
                logger.warn(
                    "Exit: {}.{}() had arguments = {}, with result = {}, Execution time = {} ms",
                    joinPoint.signature.declaringTypeName, joinPoint.signature.name,
                    joinPoint.args[0],
                    "[cancelled]", (System.currentTimeMillis() - start)
                )
            }
    }

    private fun <T> logResult(result: T, logger: Logger, joinPoint: ProceedingJoinPoint, start: Long): Any {
        val response = result.toString()
        logger.info(
            "Enter: {}.{}() with argument[s] = {}",
            joinPoint.signature.declaringTypeName, joinPoint.signature.name,
            joinPoint.args
        )
        logger.info(
            "Exit: {}.{}(), with result = {}, Execution time = {} ms",
            joinPoint.signature.declaringTypeName, joinPoint.signature.name,
            response, (System.currentTimeMillis() - start)
        )
        return joinPoint.proceed()
    }
}