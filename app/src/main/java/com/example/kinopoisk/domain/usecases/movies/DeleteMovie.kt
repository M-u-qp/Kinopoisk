package com.example.kinopoisk.domain.usecases.movies

import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.domain.repository.KinopoiskRepository

class DeleteMovie(
    private val kinopoiskRepository: KinopoiskRepository
) {

    suspend operator fun invoke(movie: Movie) {
        kinopoiskRepository.deleteMovie(movie)
    }
}