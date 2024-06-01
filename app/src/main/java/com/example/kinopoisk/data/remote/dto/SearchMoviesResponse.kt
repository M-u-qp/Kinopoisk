package com.example.kinopoisk.data.remote.dto

import com.example.kinopoisk.domain.model.Film

data class SearchMoviesResponse(
    val films: List<Film>,
    val keyword: String,
    val pagesCount: Int,
    val searchFilmsCountResult: Int
)