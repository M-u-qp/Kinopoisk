package com.example.kinopoisk.presentation.collectiondb

import com.example.kinopoisk.domain.model.Movie

data class CollectionDBState(
    val moviesCollection: List<Movie?> = emptyList()
)
