package com.example.kinopoisk.domain.usecases.movies

data class MoviesUseCases(
    val getMovie: GetMovie,
    val upsertMovie: UpsertMovie,
    val deleteMovie: DeleteMovie,
    val deleteMovieById: DeleteMovieById,
    val getAllMoviesInDB: GetAllMoviesInDB,
    val deleteCollectionMovies: DeleteCollectionMovies,
    val galleryMovie: GalleryMovie,
    val getCountriesAndGenres: GetCountriesAndGenres,
    val searchFilterMovies: SearchFilterMovies,
    val getSerialSeasons: GetSerialSeasons
)
