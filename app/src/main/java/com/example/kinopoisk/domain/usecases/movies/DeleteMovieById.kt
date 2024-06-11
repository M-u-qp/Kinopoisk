package com.example.kinopoisk.domain.usecases.movies

import com.example.kinopoisk.domain.repository.KinopoiskRepository

class DeleteMovieById(
    private val kinopoiskRepository: KinopoiskRepository
) {

    suspend operator fun invoke(id: Int) {
        kinopoiskRepository.deleteMovieById(
            id = id
        )
    }
}