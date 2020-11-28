package com.github.chrisime

import io.micronaut.runtime.mapError
import io.micronaut.runtime.startApplication

object Application

fun main(args: Array<String>) {

    startApplication<Application>(*args) {
        packages("com.github.chrisime")
        mapError<RuntimeException> { 500 }
    }
    
}

