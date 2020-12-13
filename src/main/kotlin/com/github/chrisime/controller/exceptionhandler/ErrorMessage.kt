package com.github.chrisime.controller.exceptionhandler

data class ErrorMessage(val message: String, val errorCode: String, val path: String, val timestamp: Long)
