package com.example.kinopoisk.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kinopoisk.R
import com.example.kinopoisk.data.local.MoviesDao
import com.example.kinopoisk.data.mapper.toMovie
import com.example.kinopoisk.data.remote.CollectionsPagingSource
import com.example.kinopoisk.data.remote.KinopoiskApi
import com.example.kinopoisk.data.remote.SearchMoviesPagingSource
import com.example.kinopoisk.domain.model.DailyQuota
import com.example.kinopoisk.domain.model.Film
import com.example.kinopoisk.domain.model.Item
import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow

class KinopoiskRepositoryImpl(
    private val kinopoiskApi: KinopoiskApi,
    private val context: Context,
    private val moviesDao: MoviesDao
) : KinopoiskRepository {

    override suspend fun getApiCount(): DailyQuota {
        return kinopoiskApi.checkApiCount(apiKey = context.getString(R.string.API_KEY)).dailyQuota
    }
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

    override suspend fun getMovie(id: Int): Movie {
        return kinopoiskApi.getMovie(
            apiKey = context.getString(R.string.API_KEY),
            id = id
        ).toMovie()
    }

    override suspend fun upsertMovie(movie: Movie) {
        moviesDao.upsert(movie)
    }

    override suspend fun deleteMovie(movie: Movie) {
        moviesDao.delete(movie)
    }

    override fun selectMovies(): Flow<List<Movie>> {
        return moviesDao.getMovies()
    }

    override suspend fun selectMovie(id: Int): Movie? {
        return moviesDao.getMovie(id)
    }
}

