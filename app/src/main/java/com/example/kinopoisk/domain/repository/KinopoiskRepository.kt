package com.example.kinopoisk.domain.repository

import androidx.paging.PagingData
import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.domain.model.DailyQuota
import com.example.kinopoisk.domain.model.Film
import com.example.kinopoisk.domain.model.Item
import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface KinopoiskRepository {

    //Доп
    suspend fun getApiCount(): DailyQuota

    //Получение коллекций
    fun getTopPopularAll(): Flow<PagingData<Item>>

    //Поиск
    fun searchMovies(keyword: String): Flow<PagingData<Film>>

    //Получить детальную информацию о фильме
    suspend fun getMovie(id: Int): Resource<Movie>

    //Работа с БД
    suspend fun upsertMovie(movie: Movie)

    suspend fun deleteMovie(movie: Movie)

    fun selectMovies(): Flow<List<Movie>>

    suspend fun selectMovie(id: Int): Movie?

    fun selectCollections(): Flow<List<CollectionDB>>

    suspend fun selectCollection(nameCollection: String): CollectionDB?

    suspend fun addCollection(collectionDB: CollectionDB)

    suspend fun deleteCollection(collectionDB: CollectionDB)
}