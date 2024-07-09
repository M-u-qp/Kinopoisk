package com.example.kinopoisk.presentation.search_filter

import android.os.Parcelable
import com.example.kinopoisk.domain.model.CountriesAndGenres
import com.example.kinopoisk.domain.model.CountryFilter
import com.example.kinopoisk.domain.model.GenreFilter
import com.example.kinopoisk.presentation.common.SortSearchFilter
import com.example.kinopoisk.presentation.common.TypeSearchFilter
import com.example.kinopoisk.presentation.common.listLast100Years
import kotlinx.parcelize.Parcelize

data class SearchFilterState(
    val ratingPosition: ClosedFloatingPointRange<Float> = 1f..10f,

    val listCountriesAndGenres: CountriesAndGenres? = null,
    val errorListCountriesAndGenres: String? = null,

    val showDialogCountriesOrGenres: Boolean = false,
    val selectedCountryOrGenre: String = "",
    val selectedCountry: CountryFilter = CountryFilter(id = 1, country = "США"),
    val selectedGenre: GenreFilter = GenreFilter(id = 11, genre = "боевик"),

    val showDialogDatePicker: Boolean = false,
    val yearsPosition: IntRange = listLast100Years().last()..listLast100Years().first(),

    val typeSearchFilter: TypeSearchFilter = TypeSearchFilter.ALL,
    val sortSearchFilter: SortSearchFilter = SortSearchFilter.YEAR,

    val viewedMovies: Boolean = false,
)

@Parcelize
data class FilterData(
    val ratingFrom: Int,
    val ratingTo: Int,
    val selectedCountry: List<Int>,
    val selectedGenre: List<Int>,
    val yearsFrom: Int,
    val yearsTo: Int,
    val typeSearchFilter: String,
    val sortSearchFilter: String,
    val viewedMovies: Boolean
) : Parcelable
