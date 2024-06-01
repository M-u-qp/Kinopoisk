package com.example.kinopoisk.domain.usecases.movies

import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.domain.repository.KinopoiskRepository

class GetMovie(
    private val kinopoiskRepository: KinopoiskRepository
) {

    suspend operator fun invoke(id: Int): Movie {
        return kinopoiskRepository.getMovie(id)
    }
}