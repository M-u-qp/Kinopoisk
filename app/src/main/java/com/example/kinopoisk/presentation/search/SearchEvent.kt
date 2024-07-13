package com.example.kinopoisk.presentation.search

sealed class SearchEvent {

    data class UpdateSearchQuery(val keyword: String) : SearchEvent()
    data object SearchMovies : SearchEvent()
    data class UpdateFilterQuery(
        val ratingFrom: Int,
        val ratingTo: Int,
        val selectedCountry: List<Int>,
        val selectedGenre: List<Int>,
        var yearsFrom: Int,
        val yearsTo: Int,
        val typeSearchFilter: String,
        val sortSearchFilter: String,
        val viewedMovies: Boolean,
        val keyword: String
    ) : SearchEvent()
}