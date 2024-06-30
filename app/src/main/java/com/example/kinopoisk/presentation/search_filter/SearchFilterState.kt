package com.example.kinopoisk.presentation.search_filter

import com.example.kinopoisk.domain.model.CountriesAndGenres
import com.example.kinopoisk.domain.model.CountryFilter
import com.example.kinopoisk.domain.model.GenreFilter
import com.example.kinopoisk.presentation.common.SortSearchFilter
import com.example.kinopoisk.presentation.common.TypeSearchFilter
import com.example.kinopoisk.presentation.common.listLast100Years

data class SearchFilterState(
    var ratingPosition: ClosedFloatingPointRange<Float> = 1f..10f,

    val listCountriesAndGenres: CountriesAndGenres? = null,

    var showDialogCountriesOrGenres: Boolean = false,
    var selectedCountryOrGenre: String = "",
    val selectedCountry: CountryFilter = CountryFilter(id = 1, country = "США"),
    val selectedGenre: GenreFilter = GenreFilter(id = 1, genre = "боевик"),

    var showDialogDatePicker: Boolean = false,
    var yearsPosition: IntRange = listLast100Years().first() .. listLast100Years().first(),

    val typeSearchFilter: TypeSearchFilter = TypeSearchFilter.ALL,
    val sortSearchFilter: SortSearchFilter = SortSearchFilter.YEAR,

    val viewedMovies: Boolean = false,
)
