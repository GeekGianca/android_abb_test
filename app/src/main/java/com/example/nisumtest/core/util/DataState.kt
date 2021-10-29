package com.example.nisumtest.core.util

import com.example.nisumtest.core.ErrorState

sealed class DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val ex: ErrorState) : DataState<Nothing>()
    object Empty : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}
