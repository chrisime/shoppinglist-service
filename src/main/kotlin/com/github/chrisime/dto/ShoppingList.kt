package com.github.chrisime.dto

import java.util.*

data class ShoppingListItemDto(val id: UUID, val name: String, val amount: Int, val isSelected: Boolean)

data class ShoppingListAddItemDto(val name: String, val amount: Int)
