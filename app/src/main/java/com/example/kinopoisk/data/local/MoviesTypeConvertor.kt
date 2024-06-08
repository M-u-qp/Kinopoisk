package com.example.kinopoisk.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.kinopoisk.data.local.entity.MovieEntity
import com.example.kinopoisk.domain.model.Country
import com.example.kinopoisk.domain.model.Genre
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

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

    @TypeConverter
    fun toListMovie(value: String): List<MovieEntity> {
        val listType = object : TypeToken<List<MovieEntity>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun toStringMovie(list: List<MovieEntity>): String {
        return Gson().toJson(list)
    }
}