package com.github.chrisime.service.business

import com.github.chrisime.domain.UserDomain
import com.github.chrisime.service.persistence.UserRepository
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.streams.toList

@Singleton
class UserService(@Inject private val userRepository: UserRepository) {

    fun addUser(user: UserDomain) = userRepository.create(
        user.copy(
            createdTs = LocalDateTime.now(ZoneId.of("UTC")),
            modifiedTs = LocalDateTime.now(ZoneId.of("UTC")),
        )
    )

    fun getAllUsers() = userRepository.findAll().toList()

}
