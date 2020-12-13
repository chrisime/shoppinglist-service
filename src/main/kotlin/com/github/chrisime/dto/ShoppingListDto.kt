package com.github.chrisime.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.github.chrisime.domain.ShoppingListDomain
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

data class ShoppingListDomainDto(val items: List<ShoppingListDomain>)

@Introspected
@JsonNaming(SnakeCaseStrategy::class)
data class ShoppingListAddItemDto(
    @field:Size(min = 3, max = 31) @field:NotBlank val name: String,
    @field:Positive val amount: Short,
)
