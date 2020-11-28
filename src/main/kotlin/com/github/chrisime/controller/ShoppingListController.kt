package com.github.chrisime.controller

import com.github.chrisime.dto.ShoppingListDto
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.reactivex.rxjava3.core.Flowable
import java.util.*

@Controller("/api/v1/shopping-list")
class ShoppingListController {

    @Get
    fun getShoppingList(): Flowable<ShoppingListDto> {
        return Flowable.fromIterable(shoppingList)
    }

    companion object {
        val shoppingList = listOf(
            ShoppingListDto(UUID.randomUUID(), "kaese", 1, false),
            ShoppingListDto(UUID.randomUUID(), "milch", 2, false),
            ShoppingListDto(UUID.randomUUID(), "muesli", 1, false),
            ShoppingListDto(UUID.randomUUID(), "fleisch", 1, false)
        )
    }
}
