package com.example.kinopoisk.presentation.search

sealed class SearchEvent {

    data class UpdateSearchQuery(val keyword: String) : SearchEvent()

    data object SearchMovies : SearchEvent()
}