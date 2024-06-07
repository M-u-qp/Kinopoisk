package com.example.kinopoisk.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kinopoisk.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(movie: MovieEntity)

    @Delete
    suspend fun delete(movie: MovieEntity)

    @Query("SELECT * FROM MovieEntity")
    fun getMoviesInDB(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntity WHERE kinopoiskId=:kinopoiskId")
    suspend fun getMovieInDB(kinopoiskId: Int): MovieEntity?
}