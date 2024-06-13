package com.example.kinopoisk.domain.repository

import androidx.paging.PagingData
import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.domain.model.Film
import com.example.kinopoisk.domain.model.Item
import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface KinopoiskRepository {

    //Получение коллекций
    fun getTopPopularAll(): Flow<PagingData<Item>>

    //Поиск
    fun searchMovies(keyword: String): Flow<PagingData<Film>>

    //Получить детальную информацию о фильме
    suspend fun getMovie(id: Int): Resource<Movie>

    //Работа с БД
    suspend fun upsertMovie(movie: Movie)

    suspend fun deleteMovie(movie: Movie)

    suspend fun deleteMovieById(id: Int)

    fun selectCollections(): Flow<List<CollectionDB>>

     fun selectCollection(collectionName: String): Flow<List<Movie?>>

    suspend fun addCollection(collectionDB: CollectionDB)

    suspend fun deleteCollection(collectionDB: CollectionDB)

    fun getAllMoviesInDB(): Flow<List<Movie?>>
}