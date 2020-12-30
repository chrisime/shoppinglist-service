package com.github.chrisime.controller.exceptionhandler

import java.time.LocalDateTime

data class ErrorMessage(val message: String, val errorCode: String, val path: String, val timestamp: LocalDateTime)
