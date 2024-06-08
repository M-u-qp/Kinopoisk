package com.example.kinopoisk.domain.model

data class CollectionDB(
    val id: Int,
    val nameCollection: String,
    val moviesList: List<Movie>
)
