package com.example.kinopoisk.domain.usecases.collections

import com.example.kinopoisk.domain.model.SimilarItem
import com.example.kinopoisk.domain.repository.KinopoiskRepository
import com.example.kinopoisk.domain.utils.Resource

class GetSimilarMovies(
    private val kinopoiskRepository: KinopoiskRepository
) {

    suspend operator fun invoke(id: Int): Resource<List<SimilarItem>> {
        return kinopoiskRepository.getSimilarMovies(id = id)
    }
}