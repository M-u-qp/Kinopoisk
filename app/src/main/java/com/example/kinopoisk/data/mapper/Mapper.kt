package com.example.kinopoisk.data.mapper

import com.example.kinopoisk.data.remote.dto.MovieResponse
import com.example.kinopoisk.domain.model.Country
import com.example.kinopoisk.domain.model.Genre
import com.example.kinopoisk.domain.model.Movie

fun MovieResponse.toMovie(): Movie {
    return Movie(
        countries = countries.map { country -> Country(country = country.country) },
        description = description,
        filmLength = filmLength,
        genres = genres.map { genre -> Genre(genre = genre.genre) },
        kinopoiskId = kinopoiskId,
        logoUrl = logoUrl,
        nameEn = nameEn,
        nameRu = nameRu,
        posterUrl = posterUrl,
        posterUrlPreview = posterUrlPreview,
        ratingAgeLimits = ratingAgeLimits,
        ratingKinopoisk = ratingKinopoisk,
        shortDescription = shortDescription,
        webUrl = webUrl,
        year = year
    )
}