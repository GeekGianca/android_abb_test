package com.example.nisumtest.core

data class ErrorState(
    var message: String
)

interface ErrorStateCallback {
    fun removeErrorFromStack()
}