package com.github.chrisime.controller

import com.github.chrisime.domain.ShoppingListDomain
import com.github.chrisime.dto.ShoppingListDomainDto
import com.github.chrisime.dto.ShoppingListDto
import com.github.chrisime.dto.ShoppingListUpdateDto
import com.github.chrisime.service.business.UserService
import com.github.chrisime.service.persistence.ShoppingListRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpResponse.ok
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.QueryValue
import io.micronaut.validation.Validated
import java.util.*
import javax.inject.Inject
import javax.validation.Valid
import kotlin.streams.toList

@Validated
@Controller("/api/v1/shopping-list")
class ShoppingListController(
    @Inject private val shoppingListService: ShoppingListRepository,
    @Inject private val userService: UserService,
) {

    @Get
    fun getShoppingList(): HttpResponse<ShoppingListDomainDto> {
        val lst = shoppingListService.findAll()
        return ok(ShoppingListDomainDto(lst.toList()))
    }

    @Get("/{identifier}")
    fun getByIdentifier(@QueryValue identifier: UUID): HttpResponse<ShoppingListDomain> {
        return ok(shoppingListService.findByIdentifier(identifier))
    }

    @Post
    fun addItem(@Valid @Body item: ShoppingListDto): HttpResponse<Boolean> {
        val userId = userService.findOneByUsername("chrisime").id

        val result = shoppingListService.create(
            ShoppingListDomain(
                identifier = UUID.randomUUID(),
                name = item.name,
                amount = item.amount,
                isSelected = false,
                userId = userId!!
            )
        ) > 0

        return ok(result)
    }

    @Put
    fun updateItem(@Valid @Body item: ShoppingListUpdateDto): HttpResponse<Boolean> {
        val result = shoppingListService.updateByIdentifier(item.identifier, item.name, item.amount) > 0

        return ok(result)
    }

    @Delete("/{identifier}")
    fun deleteItem(@PathVariable("identifier") identifier: UUID) {
        shoppingListService.deleteByIdentifier(identifier)
    }

}
