package com.github.chrisime.service.persistence

import com.github.chrisime.domain.ShoppingListDomain
import com.github.chrisime.tables.ShoppingList.Companion.SHOPPING_LIST
import com.github.chrisime.tables.records.ShoppingListRecord
import io.micronaut.cache.annotation.CacheConfig
import org.jooq.DSLContext
import xyz.chrisime.crood.service.CRooDService
import java.util.*
import javax.inject.Singleton

@Singleton
@CacheConfig(value = ["shopping-list"])
class ShoppingListRepository(private val dsl: DSLContext) : CRooDService<ShoppingListRecord, Long, ShoppingListDomain>(dsl) {

    fun findByIdentifier(identifier: UUID): ShoppingListDomain {
        return findOneWhere {
            SHOPPING_LIST.IDENTIFIER.eq(identifier)
        }
    }

    fun updateByIdentifier(identifier: UUID, name: String, amount: Short): Int {
        return dsl.update(SHOPPING_LIST)
            .set(SHOPPING_LIST.NAME, name)
            .set(SHOPPING_LIST.AMOUNT, amount)
            .where(SHOPPING_LIST.IDENTIFIER.eq(identifier))
            .execute()
    }

    fun deleteByIdentifier(identifier: UUID): Int {
        return deleteWhere {
            SHOPPING_LIST.IDENTIFIER.eq(identifier)
        }
    }

}
