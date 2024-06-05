package com.example.kinopoisk.domain.model


data class Movie(
    val countries: List<Country>,
    val description: String?,
    val filmLength: Int?,
    val genres: List<Genre>,
    val kinopoiskId: Int,
    val logoUrl: String?,
    val nameEn: String?,
    val nameRu: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val ratingAgeLimits: String?,
    val ratingKinopoisk: Double?,
    val shortDescription: String?,
    val webUrl: String?,
    val year: Int?
)
