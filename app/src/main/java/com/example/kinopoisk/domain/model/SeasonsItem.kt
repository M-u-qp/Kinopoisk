package com.example.kinopoisk.domain.model

data class SeasonsItem(
    val episodes: List<SeasonsEpisode>,
    val number: Int,
    val serialName: String
)