package com.example.kinopoisk.domain.repository

import androidx.paging.PagingData
import com.example.kinopoisk.domain.model.Film
import com.example.kinopoisk.domain.model.Item
import kotlinx.coroutines.flow.Flow

interface KinopoiskRepository {

    fun getTopPopularAll(): Flow<PagingData<Item>>

    fun searchMovies(keyword: String): Flow<PagingData<Film>>
}