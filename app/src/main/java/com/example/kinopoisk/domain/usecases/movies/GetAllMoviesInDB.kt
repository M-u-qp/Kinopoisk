package com.example.kinopoisk.domain.usecases.movies

import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow

class GetAllMoviesInDB(
    private val kinopoiskRepository: KinopoiskRepository
) {

    operator fun invoke(): Flow<List<Movie?>> {
        return kinopoiskRepository.getAllMoviesInDB()
    }
}