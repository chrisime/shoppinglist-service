package com.github.chrisime.service.business

import com.github.chrisime.service.persistence.UserRepository
import com.github.chrisime.service.persistence.UserRepositoryImpl
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserService(@Inject private val userRepository: UserRepositoryImpl): UserRepository by userRepository
