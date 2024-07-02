package com.example.kinopoisk.data.remote.dto

import com.example.kinopoisk.domain.model.SeasonsItem

data class SerialSeasonsResponse(
    val items: List<SeasonsItem>,
    val total: Int
)