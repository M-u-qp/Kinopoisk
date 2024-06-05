package com.example.kinopoisk.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kinopoisk.data.local.entity.MovieEntity
import com.example.kinopoisk.domain.model.Movie

@Database(entities = [MovieEntity::class], version = 1)
@TypeConverters(MoviesTypeConvertor::class)
abstract class MoviesDatabase : RoomDatabase() {

    abstract val moviesDao: MoviesDao
}