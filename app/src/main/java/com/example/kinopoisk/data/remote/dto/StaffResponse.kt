package com.example.kinopoisk.data.remote.dto

import com.example.kinopoisk.domain.model.Films
import com.example.kinopoisk.domain.model.Spouse

data class StaffResponse(
    val age: Int?,
    val birthday: String?,
    val birthplace: String?,
    val death: String?,
    val deathplace: String?,
    val facts: List<String>,
    val films: List<Films>,
    val growth: String?,
    val hasAwards: Int?,
    val nameEn: String?,
    val nameRu: String?,
    val personId: Int?,
    val posterUrl: String?,
    val profession: String?,
    val sex: String?,
    val spouses: List<Spouse>,
    val webUrl: String?
)