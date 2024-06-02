package com.example.kinopoisk.domain.usecases.movies

import com.example.kinopoisk.data.local.MoviesDao
import com.example.kinopoisk.domain.model.Movie

class UpsertMovie(
    private val moviesDao: MoviesDao
) {

    suspend operator fun invoke(movie: Movie) {
        moviesDao.upsert(movie)
    }
}