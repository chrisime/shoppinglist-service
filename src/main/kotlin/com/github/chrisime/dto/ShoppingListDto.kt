package com.github.chrisime.dto

import java.util.*

data class ShoppingListDto(val id: UUID, val name: String, val amount: Int, val isSelected: Boolean)
