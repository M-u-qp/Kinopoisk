package com.example.kinopoisk.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.kinopoisk.data.remote.CollectionsPagingSource
import com.example.kinopoisk.data.remote.KinopoiskApi
import com.example.kinopoisk.data.remote.SearchMoviesPagingSource
import com.example.kinopoisk.domain.model.Film
import com.example.kinopoisk.domain.model.Item
import com.example.kinopoisk.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow

class KinopoiskRepositoryImpl(
    private val kinopoiskApi: KinopoiskApi,
    private val context: Context
): KinopoiskRepository {
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

}