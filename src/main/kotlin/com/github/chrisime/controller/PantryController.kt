package com.github.chrisime.controller

import com.github.chrisime.dto.PantryDto
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Flowable

@Controller("/api/v1/pantry")
class PantryController {
    
    @Get
    fun getPantry(): @NonNull Flowable<PantryDto>? {
        return Flowable.fromIterable(pantry)
    }
    
    companion object {
        val pantry = listOf(
            PantryDto("ei", 12, 1),
            PantryDto("wurst", 2, 1),
            PantryDto("kaese", 3, 1),
            PantryDto("milch", 1, 1),
            PantryDto("saft", 5, 1),
            PantryDto("butter", 1, 1)
        )
    }

}
