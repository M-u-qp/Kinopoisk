package com.example.kinopoisk.data.remote.dto

import com.example.kinopoisk.domain.model.SearchFilm

data class SearchMoviesResponse(
    val films: List<SearchFilm>,
    val keyword: String,
    val pagesCount: Int,
    val searchFilmsCountResult: Int
)