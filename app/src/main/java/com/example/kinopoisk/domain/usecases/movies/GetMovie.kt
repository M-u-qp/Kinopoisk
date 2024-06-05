package com.example.kinopoisk.domain.usecases.movies

import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.domain.repository.KinopoiskRepository
import com.example.kinopoisk.domain.utils.Resource

class GetMovie(
    private val kinopoiskRepository: KinopoiskRepository
) {

    suspend operator fun invoke(id: Int): Resource<Movie> {
        return kinopoiskRepository.getMovie(id)
    }
}