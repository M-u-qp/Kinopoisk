package com.example.kinopoisk.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kinopoisk.R
import com.example.kinopoisk.data.local.MoviesDao
import com.example.kinopoisk.data.mapper.toCollectionDB
import com.example.kinopoisk.data.mapper.toCollectionEntity
import com.example.kinopoisk.data.mapper.toCountriesAndGenres
import com.example.kinopoisk.data.mapper.toMovie
import com.example.kinopoisk.data.mapper.toMovieEntity
import com.example.kinopoisk.data.mapper.toStaffInfo
import com.example.kinopoisk.data.remote.CollectionsPagingSource
import com.example.kinopoisk.data.remote.DynamicMoviesPagingSource
import com.example.kinopoisk.data.remote.GalleryMoviesPagingSource
import com.example.kinopoisk.data.remote.KinopoiskApi
import com.example.kinopoisk.data.remote.SearchFilterMoviesPagingSource
import com.example.kinopoisk.data.remote.SearchMoviesPagingSource
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
    override fun getTopPopularAll(type: String): Flow<PagingData<CollectionItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                CollectionsPagingSource(
                    kinopoiskApi = kinopoiskApi,
                    context = context,
                    type = type
                )
            }
        ).flow
    }

    override suspend fun getPremieres(year: Int, month: String): Resource<List<PremieresItem>> {
        return try {
            val response = kinopoiskApi.getPremieres(
                apiKey = context.getString(R.string.API_KEY),
                year = year,
                month = month
            )
            if (response.body() != null) {
                Resource.Success(response.body()!!.items)
            } else {
                Resource.Error(Exception(getResponseError(response.code())))
            }
        }catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    override fun getDynamicMovies(
        countries: List<Int>,
        genres: List<Int>
    ): Flow<PagingData<FilterItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                DynamicMoviesPagingSource(
                    kinopoiskApi = kinopoiskApi,
                    context = context,
                    countries = countries,
                    genres = genres
                )
            }
        ).flow
    }

    override suspend fun getSimilarMovies(id: Int): Resource<List<SimilarItem>> {
        return try {
            val response = kinopoiskApi.getSimilarMovies(
                apiKey = context.getString(R.string.API_KEY),
                id = id
            )
            if (response.body() != null) {
                Resource.Success(response.body()!!.items)
            } else {
                Resource.Error(Exception(getResponseError(response.code())))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    //Поиск
    override fun searchMovies(keyword: String): Flow<PagingData<SearchFilm>> {
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

    override fun searchFilterMovies(
        countries: List<Int>,
        genres: List<Int>,
        order: String,
        type: String,
        ratingFrom: Int,
        ratingTo: Int,
        yearFrom: Int,
        yearTo: Int
    ): Flow<PagingData<FilterItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                SearchFilterMoviesPagingSource(
                    kinopoiskApi = kinopoiskApi,
                    context = context,
                    countries = countries,
                    genres = genres,
                    order = order,
                    type = type,
                    ratingFrom = ratingFrom,
                    ratingTo = ratingTo,
                    yearFrom = yearFrom,
                    yearTo = yearTo
                )
            }
        ).flow
    }

    //Список стран и жанров для фильтра
    override suspend fun getCountriesAndGenres(): Resource<CountriesAndGenres> {
        return try {
            val response = kinopoiskApi.getCountriesAndGenres(
                apiKey = context.getString(R.string.API_KEY)
            )
            if (response.body() != null) {
                Resource.Success(response.body()!!.toCountriesAndGenres())
            } else {
                Resource.Error(Exception(getResponseError(response.code())))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    //Получить сезоны сериала
    override suspend fun getSerialSeasons(id: Int): Resource<List<SeasonsItem>> {
        return try {
            val response = kinopoiskApi.getSerialSeasons(
                apiKey = context.getString(R.string.API_KEY),
                id = id
            )
            if (response.body() != null) {
                Resource.Success(response.body()!!.items)
            } else {
                Resource.Error(Exception(getResponseError(response.code())))
            }
        }catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
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

    override suspend fun getStaff(id: Int): Resource<StaffInfo> {
        return try {
            val response = kinopoiskApi.getStaff(
                apiKey = context.getString(R.string.API_KEY),
                id = id
            )
            if (response.body() != null) {
                Resource.Success(response.body()!!.toStaffInfo())
            } else {
                Resource.Error(Exception(getResponseError(response.code())))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }

    //Галерея
    override fun getGalleryMovie(id: Int, type: String): Flow<PagingData<GalleryItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                GalleryMoviesPagingSource(
                    kinopoiskApi = kinopoiskApi,
                    context = context,
                    id = id,
                    type = type
                )
            }
        ).flow
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

