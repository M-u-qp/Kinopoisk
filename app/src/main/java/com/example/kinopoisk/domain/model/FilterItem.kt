package com.example.kinopoisk.domain.model

data class FilterItem(
    val countries: List<Country>,
    val genres: List<Genre>,
    val imdbId: String,
    val kinopoiskId: Int,
    val nameEn: String,
    val nameOriginal: String,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val ratingImdb: Double,
    val ratingKinopoisk: Double,
    val type: String,
    val year: Int
)