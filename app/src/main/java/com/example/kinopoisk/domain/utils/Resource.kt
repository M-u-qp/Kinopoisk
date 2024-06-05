package com.example.kinopoisk.domain.utils

sealed class Resource<T>(
    val data: T? = null,
    val exception: Exception? = null
) {

    class Success<T>(data: T) : Resource<T>(data = data)
    class Error<T>(e: Exception) : Resource<T>(exception = e)
    class Loading<T> : Resource<T>()
}