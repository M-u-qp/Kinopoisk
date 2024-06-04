package com.example.kinopoisk.domain.usecases.movies

import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.domain.repository.KinopoiskRepository

class SelectMovie(
    private val kinopoiskRepository: KinopoiskRepository
) {

    suspend operator fun invoke(kinopoiskId: Int): Movie? {
        return kinopoiskRepository.selectMovie(id = kinopoiskId)
    }
}