package com.example.kinopoisk.domain.usecases.collections

import androidx.paging.PagingData
import com.example.kinopoisk.domain.model.FilterItem
import com.example.kinopoisk.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow

class GetDynamicMovies(
    private val kinopoiskRepository: KinopoiskRepository
) {

    operator fun invoke(countries: List<Int>, genres: List<Int>): Flow<PagingData<FilterItem>> {
        return kinopoiskRepository.getDynamicMovies(countries = countries, genres = genres)
    }
}