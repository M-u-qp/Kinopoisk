package com.example.kinopoisk.data.remote

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.FilterItem

class SearchFilterMoviesPagingSource(
    private val kinopoiskApi: KinopoiskApi,
    private val countries: List<Int>,
    private val genres: List<Int>,
    private val order: String,
    private val type: String,
    private val ratingFrom: Int,
    private val ratingTo: Int,
    private val yearFrom: Int,
    private val yearTo: Int,
    private val keyword: String,
    private val context: Context
) : PagingSource<Int, FilterItem>() {

    private var totalMoviesCount = 0
    override fun getRefreshKey(state: PagingState<Int, FilterItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilterItem> {
        val page = params.key ?: 1
        return try {
            val moviesResponse = kinopoiskApi.searchFilterMovies(
                apiKey = context.getString(R.string.API_KEY),
                countries = countries,
                genres = genres,
                order = order,
                type = type,
                ratingFrom = ratingFrom,
                ratingTo = ratingTo,
                yearFrom = yearFrom,
                yearTo = yearTo,
                keyword = keyword,
                page = page
            )
            totalMoviesCount += moviesResponse.items.size
            val movies = moviesResponse.items
            LoadResult.Page(
                data = movies,
                nextKey = if (totalMoviesCount == moviesResponse.total) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(throwable = e)
        }
    }
}

class DynamicMoviesPagingSource(
    private val kinopoiskApi: KinopoiskApi,
    private val countries: List<Int>,
    private val genres: List<Int>,
    private val context: Context
) : PagingSource<Int, FilterItem>() {

    private var totalMoviesCount = 0
    override fun getRefreshKey(state: PagingState<Int, FilterItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilterItem> {
        val page = params.key ?: 1
        return try {
            val moviesResponse = kinopoiskApi.getDynamicMovies(
                apiKey = context.getString(R.string.API_KEY),
                countries = countries,
                genres = genres,
                page = page
            )
            totalMoviesCount += moviesResponse.items.size
            val movies = moviesResponse.items
            LoadResult.Page(
                data = movies,
                nextKey = if (totalMoviesCount == moviesResponse.total) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(throwable = e)
        }
    }
}