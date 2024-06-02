package com.example.kinopoisk.domain.usecases.movies

import com.example.kinopoisk.data.local.MoviesDao
import com.example.kinopoisk.domain.model.Movie
import kotlinx.coroutines.flow.Flow

class SelectMovies(
    private val moviesDao: MoviesDao
) {

    operator fun invoke(): Flow<List<Movie>> {
        return moviesDao.getMovies()
    }
}