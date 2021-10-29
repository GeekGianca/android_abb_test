package com.example.nisumtest.core

interface UIController {
    fun onError(errorState: ErrorState, errorStateCallback: ErrorStateCallback)
    fun onMessage()
}