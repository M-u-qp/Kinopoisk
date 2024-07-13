package com.example.kinopoisk.domain.usecases.movies

import androidx.paging.PagingData
import com.example.kinopoisk.domain.model.FilterItem
import com.example.kinopoisk.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow

class SearchFilterMovies(
    private val kinopoiskRepository: KinopoiskRepository
) {

    operator fun invoke(
        countries: List<Int>,
        genres: List<Int>,
        order: String,
        type: String,
        ratingFrom: Int,
        ratingTo: Int,
        yearFrom: Int,
        yearTo: Int,
        keyword: String
    ): Flow<PagingData<FilterItem>> {
        return kinopoiskRepository.searchFilterMovies(
            countries = countries,
            genres = genres,
            order = order,
            type = type,
            ratingFrom = ratingFrom,
            ratingTo = ratingTo,
            yearFrom = yearFrom,
            yearTo = yearTo,
            keyword = keyword
        )
    }
}