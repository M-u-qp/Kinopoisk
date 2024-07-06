package com.example.kinopoisk.domain.model

data class PremieresItem(
    val countries: List<Country>,
    val duration: Int?,
    val genres: List<Genre>,
    val kinopoiskId: Int,
    val nameEn: String?,
    val nameRu: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val premiereRu: String?,
    val year: Int?
)