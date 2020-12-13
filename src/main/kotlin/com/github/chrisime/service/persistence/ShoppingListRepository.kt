package com.github.chrisime.service.persistence

import com.github.chrisime.domain.ShoppingListDomain
import com.github.chrisime.tables.ShoppingList.SHOPPING_LIST
import com.github.chrisime.tables.records.ShoppingListRecord
import io.micronaut.cache.annotation.CacheConfig
import org.jooq.DSLContext
import xyz.chrisime.crood.service.CRUDService
import java.util.*
import javax.inject.Singleton

@Singleton
@CacheConfig(value = ["shopping-list"])
class ShoppingListRepository(dslContext: DSLContext) : CRUDService<ShoppingListRecord, Long, ShoppingListDomain>(dslContext) {
    
    fun findByIdentifier(identifier: UUID): String? {
        return fetchOneWhere(SHOPPING_LIST.NAME) {
            SHOPPING_LIST.IDENTIFIER.eq(identifier)
        }
    }

}