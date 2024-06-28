package com.example.kinopoisk.domain.model

data class CountriesAndGenres(
    val countries: List<CountryFilter>,
    val genres: List<GenreFilter>
)
