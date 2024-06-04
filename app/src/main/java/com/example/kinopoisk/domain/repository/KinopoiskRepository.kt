package com.example.kinopoisk.domain.repository

import androidx.paging.PagingData
import com.example.kinopoisk.domain.model.DailyQuota
import com.example.kinopoisk.domain.model.Film
import com.example.kinopoisk.domain.model.Item
import com.example.kinopoisk.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface KinopoiskRepository {

    suspend fun getApiCount(): DailyQuota

    fun getTopPopularAll(): Flow<PagingData<Item>>

    fun searchMovies(keyword: String): Flow<PagingData<Film>>

    suspend fun getMovie(id: Int): Movie

    suspend fun upsertMovie(movie: Movie)

    suspend fun deleteMovie(movie: Movie)

    fun selectMovies(): Flow<List<Movie>>

    suspend fun selectMovie(id: Int): Movie?
}