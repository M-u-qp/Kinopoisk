package com.example.kinopoisk.data.remote.dto

import com.example.kinopoisk.domain.model.PremieresItem

data class PremieresResponse(
    val items: List<PremieresItem>,
    val total: Int
)