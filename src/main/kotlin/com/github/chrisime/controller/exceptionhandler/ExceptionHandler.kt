package com.github.chrisime.controller.exceptionhandler

import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponse.notFound
import io.micronaut.http.HttpResponseFactory.INSTANCE
import io.micronaut.http.HttpStatus
import io.micronaut.http.server.exceptions.ExceptionHandler
import org.jooq.exception.DataAccessException
import xyz.chrisime.crood.error.DatabaseException
import xyz.chrisime.crood.error.NoResultsFoundException
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Singleton

@Singleton
@Requires(classes = [NoResultsFoundException::class])
class NoResultsFoundHandler : ExceptionHandler<NoResultsFoundException, HttpResponse<ErrorMessage>> {
    override fun handle(request: HttpRequest<*>, exception: NoResultsFoundException): HttpResponse<ErrorMessage> {
        return notFound(
            ErrorMessage(
                errorCode = ErrorCode.NOT_RESULTS_FOUND,
                message = exception.localizedMessage,
                reason = HttpStatus.NOT_ACCEPTABLE.reason,
                path = request.path,
                timestamp = LocalDateTime.now(ZoneId.of("UTC"))
            )
        )
    }
}

@Singleton
@Requires(classes = [DatabaseException::class])
class DatabaseExceptionHandler : ExceptionHandler<DatabaseException, HttpResponse<ErrorMessage>> {
    override fun handle(request: HttpRequest<*>, exception: DatabaseException): HttpResponse<ErrorMessage> {
        return notFound(
            ErrorMessage(
                errorCode = ErrorCode.NOT_FOUND,
                message = exception.localizedMessage,
                reason = HttpStatus.NOT_FOUND.reason,
                path = request.path,
                timestamp = LocalDateTime.now(ZoneId.of("UTC"))
            )
        )
    }
}

@Singleton
@Requires(classes = [DataAccessException::class])
class DuplicateKeyExceptionHandler : ExceptionHandler<DataAccessException, HttpResponse<ErrorMessage>> {
    override fun handle(request: HttpRequest<*>, exception: DataAccessException): HttpResponse<ErrorMessage> {
        return INSTANCE.status<ErrorMessage>(HttpStatus.CONFLICT)
            .body(
                ErrorMessage(
                    errorCode = ErrorCode.NOT_FOUND,
                    message = exception.localizedMessage,
                    reason = HttpStatus.NOT_FOUND.reason,
                    path = request.path,
                    timestamp = LocalDateTime.now(ZoneId.of("UTC"))
                )
            )
    }
}
