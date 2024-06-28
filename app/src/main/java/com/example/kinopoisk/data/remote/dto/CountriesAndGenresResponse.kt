package com.example.kinopoisk.data.remote.dto

import com.example.kinopoisk.domain.model.CountryFilter
import com.example.kinopoisk.domain.model.GenreFilter

data class CountriesAndGenresResponse(
    val countries: List<CountryFilter>,
    val genres: List<GenreFilter>
)