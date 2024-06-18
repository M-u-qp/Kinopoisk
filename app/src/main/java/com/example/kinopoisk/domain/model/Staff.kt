package com.example.kinopoisk.domain.model

data class Staff(
    val staffId: Int?,
    val nameEn: String?,
    val nameRu: String?,
    val description: String?,
    val posterUrl: String?,
    val professionKey: String?,
    val professionText: String?
)