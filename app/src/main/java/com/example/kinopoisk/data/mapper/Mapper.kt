package com.example.kinopoisk.data.mapper

import com.example.kinopoisk.data.local.entity.CollectionEntity
import com.example.kinopoisk.data.local.entity.MovieEntity
import com.example.kinopoisk.data.remote.dto.CountriesAndGenresResponse
import com.example.kinopoisk.data.remote.dto.MovieResponse
import com.example.kinopoisk.data.remote.dto.StaffResponse
import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.domain.model.CountriesAndGenres
import com.example.kinopoisk.domain.model.Country
import com.example.kinopoisk.domain.model.FilterItem
import com.example.kinopoisk.domain.model.Genre
import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.domain.model.SearchFilm
import com.example.kinopoisk.domain.model.StaffInfo

fun MovieResponse.toMovie(): Movie {
    return Movie(
        id = 0,
        collectionName = "",
        countries = countries.map { country -> Country(country = country.country) },
        description = description,
        filmLength = filmLength,
        genres = genres.map { genre -> Genre(genre = genre.genre) },
        kinopoiskId = kinopoiskId,
        logoUrl = logoUrl,
        nameEn = nameEn,
        nameRu = nameRu,
        nameOriginal = nameOriginal,
        posterUrl = posterUrl,
        posterUrlPreview = posterUrlPreview,
        ratingAgeLimits = ratingAgeLimits,
        ratingKinopoisk = ratingKinopoisk,
        shortDescription = shortDescription,
        webUrl = webUrl,
        year = year,
        type = type
    )
}

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        collectionName = collectionName,
        countries = countries.map { country -> Country(country = country.country) },
        description = description,
        filmLength = filmLength,
        genres = genres.map { genre -> Genre(genre = genre.genre) },
        kinopoiskId = kinopoiskId,
        logoUrl = logoUrl,
        nameEn = nameEn,
        nameRu = nameRu,
        nameOriginal = nameOriginal,
        posterUrl = posterUrl,
        posterUrlPreview = posterUrlPreview,
        ratingAgeLimits = ratingAgeLimits,
        ratingKinopoisk = ratingKinopoisk,
        shortDescription = shortDescription,
        webUrl = webUrl,
        year = year,
        type = type
    )
}

fun Movie.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        collectionName = collectionName,
        countries = countries.map { country -> Country(country = country.country) },
        description = description.toString(),
        filmLength = filmLength ?: 0,
        genres = genres.map { genre -> Genre(genre = genre.genre) },
        kinopoiskId = kinopoiskId,
        logoUrl = logoUrl.toString(),
        nameEn = nameEn.toString(),
        nameRu = nameRu.toString(),
        nameOriginal = nameOriginal.toString(),
        posterUrl = posterUrl.toString(),
        posterUrlPreview = posterUrlPreview.toString(),
        ratingAgeLimits = ratingAgeLimits.toString(),
        ratingKinopoisk = ratingKinopoisk ?: 0.0,
        shortDescription = shortDescription.toString(),
        webUrl = webUrl.toString(),
        year = year ?: 0,
        type = type.toString()
    )
}

fun CollectionDB.toCollectionEntity(): CollectionEntity {
    return CollectionEntity(
        id = id,
        nameCollection = nameCollection
    )
}

fun CollectionEntity.toCollectionDB(): CollectionDB {
    return CollectionDB(
        id = id,
        nameCollection = nameCollection
    )
}

fun StaffResponse.toStaffInfo(): StaffInfo {
    return StaffInfo(
        age = age,
        birthday = birthday,
        birthplace = birthplace,
        death = death,
        deathplace = deathplace,
        facts = facts,
        films = films,
        growth = growth,
        hasAwards = hasAwards,
        nameEn = nameEn,
        nameRu = nameRu,
        personId = personId,
        posterUrl = posterUrl,
        profession = profession,
        sex = sex,
        spouses = spouses,
        webUrl = webUrl
    )
}

fun CountriesAndGenresResponse.toCountriesAndGenres(): CountriesAndGenres {
    return CountriesAndGenres(
        countries = countries,
        genres = genres
    )
}

fun FilterItem.toSearchFilm(): SearchFilm {
    return SearchFilm(
        countries = countries,
        genres = genres,
        description = "",
        filmId = kinopoiskId,
        filmLength = "",
        nameEn = nameEn,
        nameRu = nameRu,
        nameOriginal = nameOriginal,
        posterUrl = posterUrl,
        posterUrlPreview = posterUrlPreview,
        rating = ratingKinopoisk.toString(),
        ratingVoteCount = 0,
        type = type,
        year = year.toString()
    )
}