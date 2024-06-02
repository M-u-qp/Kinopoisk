package com.example.kinopoisk.presentation.bookmark

import com.example.kinopoisk.domain.model.Movie

data class BookmarkState(
    val movies: List<Movie> = emptyList()
)
