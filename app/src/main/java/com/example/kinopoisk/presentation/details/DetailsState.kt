package com.example.kinopoisk.presentation.details

import com.example.kinopoisk.domain.model.Movie

data class DetailsState(
    val id: Int = 0,
    val movie: Movie? = null
)