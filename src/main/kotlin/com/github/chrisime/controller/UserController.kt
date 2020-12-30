package com.github.chrisime.controller

import com.github.chrisime.domain.UserDomain
import com.github.chrisime.service.business.UserService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.inject.Inject

@Validated
@Controller("/api/v1/user")
class UserController(@Inject private val userService: UserService) {

    @Get
    fun getAll(): HttpResponse<List<UserDomain>> {
        return HttpResponse.ok(userService.getAllUsers())
    }

    @Post
    fun create(user: UserDomain): HttpResponse<Boolean> {
        return HttpResponse.created(userService.addUser(user) > 0)
    }

}
