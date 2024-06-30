package com.example.kinopoisk.presentation.search

sealed class SearchEvent {

    data class UpdateSearchQuery(val keyword: String) : SearchEvent()
    data object SearchMovies : SearchEvent()
    data class UpdateFilterQueryAndSearch(
        val ratingFrom: Int,
        val ratingTo: Int,
        val selectedCountry: List<Int>,
        val selectedGenre: List<Int>,
        var yearsFrom: Int,
        val yearsTo: Int,
        val typeSearchFilter: String,
        val sortSearchFilter: String,
    ) : SearchEvent()
}