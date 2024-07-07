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
    var ratingPosition: ClosedFloatingPointRange<Float> = 1f..10f,

    val listCountriesAndGenres: CountriesAndGenres? = null,
    val errorListCountriesAndGenres: String? = null,

    var showDialogCountriesOrGenres: Boolean = false,
    var selectedCountryOrGenre: String = "",
    val selectedCountry: CountryFilter = CountryFilter(id = 1, country = "США"),
    val selectedGenre: GenreFilter = GenreFilter(id = 11, genre = "боевик"),

    var showDialogDatePicker: Boolean = false,
    var yearsPosition: IntRange = listLast100Years().last()..listLast100Years().first(),

    val typeSearchFilter: TypeSearchFilter = TypeSearchFilter.ALL,
    val sortSearchFilter: SortSearchFilter = SortSearchFilter.YEAR,

    val viewedMovies: Boolean = false,

    val filterData: FilterData? = null
)

@Parcelize
data class FilterData(
    val ratingFrom: Int = 1,
    val ratingTo: Int = 10,
    val selectedCountry: List<Int> = listOf(1),
    val selectedGenre: List<Int> = listOf(11),
    var yearsFrom: Int = listLast100Years().last(),
    val yearsTo: Int = listLast100Years().first(),
    val typeSearchFilter: String = TypeSearchFilter.ALL.name,
    val sortSearchFilter: String = SortSearchFilter.YEAR.name,
    val viewedMovies: Boolean = false
) : Parcelable
