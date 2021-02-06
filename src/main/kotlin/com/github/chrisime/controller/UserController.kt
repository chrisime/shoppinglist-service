package com.github.chrisime.controller

import com.github.chrisime.domain.UserDomain
import com.github.chrisime.service.business.UserService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.QueryValue
import io.micronaut.validation.Validated
import javax.inject.Inject

@Validated
@Controller("/api/v1/user")
class UserController(@Inject private val userService: UserService) {

    @Get("/all")
    fun getAll(): HttpResponse<List<UserDomain>> {
        return HttpResponse.ok(userService.findAllUsers())
    }

    @Get
    fun getByUsername(@QueryValue("username") username: String): HttpResponse<UserDomain>? {
        return HttpResponse.ok(userService.findOneByUsername(username))
    }

    @Post
    fun create(user: UserDomain): HttpResponse<Boolean> {
        return HttpResponse.created(userService.addOne(user))
    }

    @Delete
    fun remove(@QueryValue("username") username: String): HttpResponse<Boolean> {
        return HttpResponse.ok(userService.deleteOneByUsername(username))
    }

    @Put
    fun modify(user: UserDomain): HttpResponse<Boolean> {
        return HttpResponse.ok(userService.modifyOne(user))
    }

}
