package com.example.kinopoisk.data.remote

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.Film

class SearchMoviesPagingSource(
    private val kinopoiskApi: KinopoiskApi,
    private val keyword: String,
    private val context: Context
) : PagingSource<Int, Film>() {

    private var totalMoviesCount = 0
    override fun getRefreshKey(state: PagingState<Int, Film>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Film> {
        val page = params.key ?: 1
        return try {
            val moviesResponse = kinopoiskApi.searchMovies(
                apiKey = context.getString(R.string.API_KEY),
                keyword = keyword,
                page = page
            )
            totalMoviesCount += moviesResponse.films.size
            val movies = moviesResponse.films
            LoadResult.Page(
                data = movies,
                nextKey = if (totalMoviesCount == moviesResponse.searchFilmsCountResult) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(throwable = e)
        }
    }


}