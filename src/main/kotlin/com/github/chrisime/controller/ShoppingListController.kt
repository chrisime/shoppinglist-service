package com.github.chrisime.controller

import com.github.chrisime.dto.ShoppingListAddItemDto
import com.github.chrisime.dto.ShoppingListItemDto
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import java.util.*

@Controller("/api/v1/shopping-list")
class ShoppingListController {

    @Get
    fun getShoppingList(): Flowable<ShoppingListItemDto> {
        return Flowable.fromIterable(shoppingList)
    }

    @Post
    fun addItem(item: ShoppingListAddItemDto): Single<Boolean> {
        val result = shoppingList.add(ShoppingListItemDto(UUID.randomUUID(), item.name, item.amount, false))
        
        return Single.just(result)
    }

    companion object {
        val shoppingList = mutableListOf(
            ShoppingListItemDto(UUID.randomUUID(), "kaese", 1, false),
            ShoppingListItemDto(UUID.randomUUID(), "milch", 2, false),
            ShoppingListItemDto(UUID.randomUUID(), "muesli", 1, false),
            ShoppingListItemDto(UUID.randomUUID(), "fleisch", 1, false)
        )
    }
}
