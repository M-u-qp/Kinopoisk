package com.example.kinopoisk.domain.model

data class StaffInfo(
    val age: Int?,
    val birthday: String?,
    val birthplace: String?,
    val death: String?,
    val deathplace: String?,
    val facts: List<String>,
    val films: List<StaffFilms>,
    val growth: String?,
    val hasAwards: Int?,
    val nameEn: String?,
    val nameRu: String?,
    val personId: Int?,
    val posterUrl: String?,
    val profession: String?,
    val sex: String?,
    val spouses: List<StaffSpouse>,
    val webUrl: String?
)
