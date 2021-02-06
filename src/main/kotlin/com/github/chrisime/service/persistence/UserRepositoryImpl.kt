package com.github.chrisime.service.persistence

import com.github.chrisime.domain.UserDomain
import com.github.chrisime.tables.records.UserRecord
import com.github.chrisime.tables.references.USER
import org.jooq.DSLContext
import xyz.chrisime.crood.service.CRooDService
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Singleton
import kotlin.streams.toList

@Singleton
class UserRepositoryImpl(private val dsl: DSLContext) : CRooDService<UserRecord, UUID, UserDomain>(dsl), UserRepository {

    override fun findAllUsers(): List<UserDomain> {
        return super.findAll().toList()
    }

    override fun findOneByUsername(username: String): UserDomain {
        return findOneWhere {
            USER.USERNAME.eq(username)
        }
    }

    override fun addOne(user: UserDomain): Boolean {
        val withTStamp = user.copy(
            createdTs = LocalDateTime.now(ZoneId.of("UTC")),
            modifiedTs = LocalDateTime.now(ZoneId.of("UTC")),
        )
        return create(withTStamp) > 0
    }

    override fun deleteOneByUsername(username: String): Boolean {
        return deleteWhere {
            USER.USERNAME.eq(username)
        } > 0
    }

    override fun modifyOne(user: UserDomain): Boolean {
        return dsl.update(USER)
            .set(USER.USERNAME, user.username)
            .execute() > 0
    }

}
