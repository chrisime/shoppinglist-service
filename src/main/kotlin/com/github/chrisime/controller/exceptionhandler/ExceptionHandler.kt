package com.github.chrisime.controller.exceptionhandler

import com.github.chrisime.exception.ShoppingListNotFoundException
import io.micronaut.context.annotation.Requires
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponse.notFound
import io.micronaut.http.HttpStatus
import io.micronaut.http.server.exceptions.ExceptionHandler
import xyz.chrisime.crood.error.GenericError
import java.time.Instant
import javax.inject.Singleton

@Singleton
@Requires(classes = [ShoppingListNotFoundException::class])
class ShoppingListExceptionHandler : ExceptionHandler<ShoppingListNotFoundException, HttpResponse<ErrorMessage>> {

    override fun handle(request: HttpRequest<*>, exception: ShoppingListNotFoundException): HttpResponse<ErrorMessage> {
        return notFound(
            ErrorMessage(exception.localizedMessage, "Not found", request.path,
            Instant.now().toEpochMilli())
        )
    }

}

@Singleton
@Requires(classes = [GenericError.Database::class])
class DatabaseExceptionHandler : ExceptionHandler<GenericError.Database, HttpResponse<ErrorMessage>> {

    override fun handle(request: HttpRequest<*>, exception: GenericError.Database): HttpResponse<ErrorMessage> {
        return notFound(
            ErrorMessage(exception.localizedMessage, HttpStatus.NOT_FOUND.reason, request.path,
            Instant.now().toEpochMilli())
        )
    }

}