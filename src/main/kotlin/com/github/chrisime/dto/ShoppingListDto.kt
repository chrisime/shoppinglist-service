package com.github.chrisime.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.github.chrisime.domain.ShoppingListDomain
import io.micronaut.core.annotation.Introspected
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Positive
import javax.validation.constraints.Size

data class ShoppingListDomainDto(val items: List<ShoppingListDomain>)

@Introspected
@JsonNaming(SnakeCaseStrategy::class)
data class ShoppingListDto(
    val identifier: UUID? = null,
    @field:Size(min = 3, max = 31) @field:NotBlank val name: String,
    @field:Positive val amount: Short,
)

@Introspected
data class ShoppingListUpdateDto(
    @field:NotBlank @field:NotNull val identifier: UUID,
    @field:Size(min = 3, max = 31) @field:NotBlank val name: String,
    @field:Positive val amount: Short,
)

@Introspected
data class ShoppingListDeletionDto(
    @field:NotBlank @field:NotNull val identifier: UUID
)