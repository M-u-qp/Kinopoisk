package com.example.kinopoisk.domain.usecases.movies

data class MoviesUseCases(
    val searchMovies: SearchMovies,
    val getMovie: GetMovie,
    val upsertMovie: UpsertMovie,
    val deleteMovie: DeleteMovie,
    val selectMovies: SelectMovies,
    val selectMovie: SelectMovie
)
