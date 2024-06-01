package com.example.kinopoisk.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.kinopoisk.domain.model.Country
import com.example.kinopoisk.domain.model.Genre

@ProvidedTypeConverter
class MoviesTypeConvertor {

    @TypeConverter
    fun genreToString(genre: List<Genre>): String {
        return genre.joinToString(",") { it.genre }
    }

    @TypeConverter
    fun stringToGenre(genre: String): List<Genre> {
        return genre.split(",").map { Genre(genre = it.trim()) }
    }

    @TypeConverter
    fun countryToString(country: List<Country>): String {
        return country.joinToString(",") { it.country }
    }

    @TypeConverter
    fun stringToCountry(country: String): List<Country> {
        return country.split(",").map { Country(country = it.trim()) }
    }
}