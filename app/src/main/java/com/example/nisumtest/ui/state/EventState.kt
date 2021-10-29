package com.example.nisumtest.ui.state

interface EventState {
    fun errorInfo(): String

    fun eventName(): String

    fun shouldDisplayProgressBar(): Boolean
}