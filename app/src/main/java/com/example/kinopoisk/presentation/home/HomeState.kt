package com.example.kinopoisk.presentation.home

import androidx.paging.PagingData
import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.domain.model.CollectionItem
import com.example.kinopoisk.domain.model.CountriesAndGenres
import com.example.kinopoisk.domain.model.FilterItem
import com.example.kinopoisk.domain.model.PremieresItem
import kotlinx.coroutines.flow.Flow

data class HomeState(
    val listCollections: List<CollectionDB> = emptyList(),
    val popularMovies: Flow<PagingData<CollectionItem>>? = null,
    val popularSerials: Flow<PagingData<CollectionItem>>? = null,
    val dynamicMovies1: Flow<PagingData<FilterItem>>? = null,
    val dynamicMovies2: Flow<PagingData<FilterItem>>? = null,

    val countriesAndGenres: CountriesAndGenres? = null,
    val errorCountriesAndGenres: String? = null,

    val randomCountryAndGenre1: String = "",
    val randomCountryAndGenre2: String = "",

    val premieres: List<PremieresItem> = emptyList(),
    val errorPremieres: String? = null,
)