package com.example.kinopoisk.domain.model

data class Film(
    val countries: List<Country?> = emptyList(),
    val description: String? = null,
    val filmId: Int = 0,
    val filmLength: String,
    val genres: List<Genre?> = emptyList(),
    val nameEn: String? = null,
    val nameRu: String? = null,
    val posterUrl: String? = null,
    val posterUrlPreview: String? = null,
    val rating: String? = null,
    val ratingVoteCount: Int? = null,
    val type: String = "",
    val year: String = ""
)