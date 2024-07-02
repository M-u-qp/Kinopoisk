package com.example.kinopoisk.domain.model

data class SeasonsEpisode(
    val episodeNumber: Int,
    val nameEn: String,
    val nameRu: String,
    val releaseDate: String,
    val seasonNumber: Int,
    val synopsis: String
)