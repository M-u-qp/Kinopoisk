package com.example.kinopoisk.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kinopoisk.R
import com.example.kinopoisk.data.local.MoviesDao
import com.example.kinopoisk.data.mapper.toCollectionDB
import com.example.kinopoisk.data.mapper.toCollectionEntity
import com.example.kinopoisk.data.mapper.toMovie
import com.example.kinopoisk.data.mapper.toMovieEntity
import com.example.kinopoisk.data.remote.CollectionsPagingSource
import com.example.kinopoisk.data.remote.KinopoiskApi
import com.example.kinopoisk.data.remote.SearchMoviesPagingSource
import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.domain.model.Film
import com.example.kinopoisk.domain.model.Item
import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.domain.model.Staff
import com.example.kinopoisk.domain.repository.KinopoiskRepository
import com.example.kinopoisk.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class KinopoiskRepositoryImpl(
    private val kinopoiskApi: KinopoiskApi,
    private val context: Context,
    private val moviesDao: MoviesDao
) : KinopoiskRepository {

    private fun getResponseError(responseCode: Int): String {
        val strResponseCode = responseCode.toString()
        return when (strResponseCode.drop(0)) {
            "4" -> "Проблема с интернетом"
            "5" -> "Ошибка сервера"
            else -> "Неизвестная ошибка"
        }
    }

    //Коллекции
    override fun getTopPopularAll(): Flow<PagingData<Item>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                CollectionsPagingSource(
                    kinopoiskApi = kinopoiskApi,
                    context = context
                )
            }
        ).flow
    }

    //Поиск
    override fun searchMovies(keyword: String): Flow<PagingData<Film>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchMoviesPagingSource(
                    kinopoiskApi = kinopoiskApi,
                    keyword = keyword,
                    context = context
                )
            }
        ).flow
    }

    //Детальное инфо о фильме
    override suspend fun getMovie(id: Int): Resource<Movie> {
        return try {
            val response = kinopoiskApi.getMovie(
                apiKey = context.getString(R.string.API_KEY),
                id = id
            )
            if (response.body() != null) {
                Resource.Success(response.body()!!.toMovie())
            } else {
                Resource.Error(Exception(getResponseError(response.code())))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    //Актеры и т.п.
    override suspend fun getListStaff(filmId: Int): Resource<List<Staff>> {
        return try {
            val response = kinopoiskApi.getListStaff(
                apiKey = context.getString(R.string.API_KEY),
                filmId = filmId
            )
            if (response.body() != null) {
                Resource.Success(response.body()!!)
            } else {
                Resource.Error(Exception(getResponseError(response.code())))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    //БД
    override suspend fun upsertMovie(movie: Movie) {
        moviesDao.upsert(
            movie = movie.toMovieEntity()
        )
    }

    override suspend fun deleteMovie(movie: Movie) {
        moviesDao.delete(
            movie.toMovieEntity()
        )
    }

    override suspend fun deleteMovieById(id: Int) {
        moviesDao.deleteMovieById(id)
    }

    override fun selectCollections(): Flow<List<CollectionDB>> {
        return moviesDao.getCollectionsInDB()
            .map { list -> list.map { collections -> collections.toCollectionDB() } }
    }

    override fun selectCollection(collectionName: String): Flow<List<Movie?>> {
        return moviesDao.getCollectionInDB(collectionName)
            .map { list -> list.map { movieEntity -> movieEntity?.toMovie() } }
    }

    override suspend fun addCollection(collectionDB: CollectionDB) {
        moviesDao.addCollection(collectionDB.toCollectionEntity())
    }

    override suspend fun deleteCollection(collectionDB: CollectionDB) {
        moviesDao.deleteCollection(collectionDB.toCollectionEntity())
    }

    override fun getAllMoviesInDB(): Flow<List<Movie?>> {
        return moviesDao.getAllMoviesInDB().map { listMovies -> listMovies.map { it?.toMovie() } }
    }

    override suspend fun deleteCollectionMovies(collectionName: String) {
        moviesDao.deleteCollectionMovies(collectionName)
    }
}

