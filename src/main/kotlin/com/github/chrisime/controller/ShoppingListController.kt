package com.github.chrisime.controller

import com.github.chrisime.domain.ShoppingListDomain
import com.github.chrisime.dto.ShoppingListAddItemDto
import com.github.chrisime.dto.ShoppingListDomainDto
import com.github.chrisime.service.persistence.ShoppingListRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponse.ok
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Inject
import javax.validation.Valid
import kotlin.streams.toList

@Validated
@Controller("/api/v1/shopping-list")
class ShoppingListController(@Inject private val shoppingListDomainService: ShoppingListRepository) {

    @Get
    fun getShoppingList(): HttpResponse<ShoppingListDomainDto> {
        val lst = shoppingListDomainService.findAll().filter {
            !it.isSelected
        }
        return ok(ShoppingListDomainDto(lst.toList()))
    }

    @Get("/{identifier}")
    fun getByIdentifier(@QueryValue identifier: UUID) : HttpResponse<ShoppingListDomain> {
        return ok(shoppingListDomainService.findByIdentifier(identifier))
    }

    @Post(consumes = [MediaType.APPLICATION_JSON])
    fun addItem(@Valid @Body item: ShoppingListAddItemDto): HttpResponse<Boolean> {
        val result = shoppingListDomainService.create(
            ShoppingListDomain(
                null,
                UUID.randomUUID(),
                item.name,
                item.amount,
                false,
                12345,
                1234,
            )
        ) > 0

        return ok(result)
    }

}
