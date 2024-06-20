package com.example.kinopoisk.domain.usecases.movies

import androidx.paging.PagingData
import com.example.kinopoisk.domain.model.SearchFilm
import com.example.kinopoisk.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow

class SearchMovies(
    private val kinopoiskRepository: KinopoiskRepository
) {

    operator fun invoke(keyword: String): Flow<PagingData<SearchFilm>> {
        return kinopoiskRepository.searchMovies(keyword = keyword)
    }
}