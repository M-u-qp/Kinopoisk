package com.example.kinopoisk.domain.repository

import androidx.paging.PagingData
import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.domain.model.SearchFilm
import com.example.kinopoisk.domain.model.GalleryItem
import com.example.kinopoisk.domain.model.CollectionItem
import com.example.kinopoisk.domain.model.CountriesAndGenres
import com.example.kinopoisk.domain.model.FilterItem
import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.domain.model.PremieresItem
import com.example.kinopoisk.domain.model.SeasonsItem
import com.example.kinopoisk.domain.model.SimilarItem
import com.example.kinopoisk.domain.model.Staff
import com.example.kinopoisk.domain.model.StaffInfo
import com.example.kinopoisk.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface KinopoiskRepository {

    //Получение коллекций
    fun getTopPopularAll(type: String): Flow<PagingData<CollectionItem>>

    suspend fun getPremieres(year: Int, month: String): Resource<List<PremieresItem>>

    suspend fun getSimilarMovies(id: Int): Resource<List<SimilarItem>>

    fun getDynamicMovies(
        countries: List<Int>,
        genres: List<Int>
    ): Flow<PagingData<FilterItem>>

    //Поиск
    fun searchMovies(keyword: String): Flow<PagingData<SearchFilm>>
    fun searchFilterMovies(
        countries: List<Int>,
        genres: List<Int>,
        order: String,
        type: String,
        ratingFrom: Int,
        ratingTo: Int,
        yearFrom: Int,
        yearTo: Int
    ): Flow<PagingData<FilterItem>>

    //Список стран и жанров для фильтра
    suspend fun getCountriesAndGenres(): Resource<CountriesAndGenres>

    //Получить сезоны сериала
    suspend fun getSerialSeasons(id: Int): Resource<List<SeasonsItem>>

    //Получить детальную информацию о фильме
    suspend fun getMovie(id: Int): Resource<Movie>

    //Актеры и т.п.
    suspend fun getListStaff(filmId: Int): Resource<List<Staff>>

    suspend fun getStaff(id: Int): Resource<StaffInfo>

    //Галерея
    fun getGalleryMovie(id: Int, type: String): Flow<PagingData<GalleryItem>>

    //Работа с БД
    suspend fun upsertMovie(movie: Movie)

    suspend fun deleteMovie(movie: Movie)

    suspend fun deleteMovieById(id: Int)

    fun selectCollections(): Flow<List<CollectionDB>>

    fun selectCollection(collectionName: String): Flow<List<Movie?>>

    suspend fun addCollection(collectionDB: CollectionDB)

    suspend fun deleteCollection(collectionDB: CollectionDB)

    fun getAllMoviesInDB(): Flow<List<Movie?>>

    suspend fun deleteCollectionMovies(collectionName: String)
}