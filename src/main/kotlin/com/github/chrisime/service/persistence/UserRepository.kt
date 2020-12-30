package com.github.chrisime.service.persistence

import com.github.chrisime.domain.UserDomain
import com.github.chrisime.tables.records.UserRecord
import com.github.chrisime.tables.references.USER
import org.jooq.DSLContext
import xyz.chrisime.crood.service.CRooDService
import java.util.*
import javax.inject.Singleton
import kotlin.streams.toList

@Singleton
class UserRepository(private val dsl: DSLContext) : CRooDService<UserRecord, UUID, UserDomain>(dsl) {

    fun insertInto(user: UserDomain): Int {
        val nonNullableFields = USER.fieldStream().filter {
            !it.dataType.nullable() && !it.dataType.defaulted()
        }.toList()

        return dsl.insertInto(USER)
            .columns(nonNullableFields)
            .values(user.firstname, user.lastname, user.username, user.email)
            .execute()
    }

    fun findByUsername(username: String): UserDomain {
        return findOneWhere {
            USER.USERNAME.eq(username)
        }
    }

}
