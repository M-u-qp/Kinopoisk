package com.example.kinopoisk.presentation.search_filter

import com.example.kinopoisk.domain.model.CountriesAndGenres
import com.example.kinopoisk.domain.model.CountryFilter
import com.example.kinopoisk.domain.model.GenreFilter

data class SearchFilterState(
    var ratingPosition: ClosedFloatingPointRange<Float> = 1f..10f,

    val listCountriesAndGenres: CountriesAndGenres? = null,

    var showDialogCountriesOrGenres: Boolean = false,
    var selectedCountryOrGenre: String = "",
    val selectedCountry: CountryFilter = CountryFilter(id = 1, country = "США"),
    val selectedGenre: GenreFilter = GenreFilter(id = 1, genre = "боевик"),

    var showDialogDatePicker: Boolean = false,
)
