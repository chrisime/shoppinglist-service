package com.github.chrisime.service.persistence

import com.github.chrisime.domain.UserDomain

interface UserRepository {

    fun findAllUsers(): List<UserDomain>

    fun findOneByUsername(username: String): UserDomain

    fun addOne(user: UserDomain): Boolean

    fun deleteOneByUsername(username: String): Boolean

    fun modifyOne(user: UserDomain): Boolean

}
