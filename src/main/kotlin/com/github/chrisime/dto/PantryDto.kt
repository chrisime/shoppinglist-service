package com.github.chrisime.dto

import io.micronaut.core.annotation.Introspected

@Introspected
data class PantryDto(val name: String, val amount: Int, val since: Long)
