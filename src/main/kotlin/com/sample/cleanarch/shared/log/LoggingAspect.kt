package com.sample.cleanarch.shared.log

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.logging.LogLevel
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
                else -> {
                    logResult(result, logger, joinPoint, start, LogLevel.INFO.name)
                    joinPoint.proceed()
                }
            }
        } catch (e: Throwable) {
            exception = e
            throw e
        } finally {
            if (result !is Mono<*> && result !is Flux<*>) {
                logResult(result, logger, joinPoint, start, LogLevel.ERROR.name, exception)
                joinPoint.proceed()
            }
        }
    }

    private fun <T> logMono(mono: Mono<T>, logger: Logger, joinPoint: ProceedingJoinPoint, start: Long): Mono<T> {
        return mono
            .switchIfEmpty(Mono.empty<T>()
                .doOnSuccess {
                    logResult("[empty]", logger, joinPoint, start, LogLevel.WARN.name)
                })
            .doOnSuccess { monoOutput: T ->
                val response = if (monoOutput !== null) monoOutput.toString() else ""
                logResult(response, logger, joinPoint, start, LogLevel.INFO.name)
            }
            .doOnError { exception ->
                logResult("[error]", logger, joinPoint, start, LogLevel.ERROR.name, exception)
            }
            .doOnCancel {
                logResult("[canceled]", logger, joinPoint, start, LogLevel.WARN.name)
            }
    }

    private fun <T> logFlux(flux: Flux<T>, logger: Logger, joinPoint: ProceedingJoinPoint, start: Long): Flux<T> {
        return flux
            .switchIfEmpty(Flux.empty<T>()
                .doOnComplete {
                    logResult("[empty]", logger, joinPoint, start, LogLevel.WARN.name)
                })
            .doOnNext { fluxOutput ->
                val response = if (fluxOutput !== null) fluxOutput.toString() else ""
                logResult(response, logger, joinPoint, start, LogLevel.INFO.name)
            }
            .doOnError { exception ->
                logResult("[error]", logger, joinPoint, start, LogLevel.ERROR.name, exception)
            }
            .doOnCancel {
                logResult("[canceled]", logger, joinPoint, start, LogLevel.WARN.name)
            }
    }

    private fun <T> logResult(
        result: T,
        logger: Logger,
        joinPoint: ProceedingJoinPoint,
        start: Long,
        level: String,
        exception: Throwable? = null
    ) {
        val response = result.toString()
        when (level) {
            "ERROR" -> {
                logger.error(
                    "Enter: {}.{}() with argument[s] = {}",
                    joinPoint.signature.declaringTypeName, joinPoint.signature.name,
                    joinPoint.args
                )
                logger.error(
                    "Exit: {}.{}(), with error = {}, Execution time = {} ms",
                    joinPoint.signature.declaringTypeName, joinPoint.signature.name,
                    exception?.message, (System.currentTimeMillis() - start)
                )
            }

            "WARN" -> {
                logger.warn(
                    "Enter: {}.{}() with argument[s] = {}",
                    joinPoint.signature.declaringTypeName, joinPoint.signature.name,
                    joinPoint.args
                )
                logger.warn(
                    "Exit: {}.{}(), with result = {}, Execution time = {} ms",
                    joinPoint.signature.declaringTypeName, joinPoint.signature.name,
                    response, (System.currentTimeMillis() - start)
                )
            }

            else -> {
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
        }
    }
}