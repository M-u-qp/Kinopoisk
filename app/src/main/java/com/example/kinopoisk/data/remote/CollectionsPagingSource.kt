package com.example.kinopoisk.data.remote

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.CollectionItem

class CollectionsPagingSource(
    private val kinopoiskApi: KinopoiskApi,
    private val context: Context,
    private val type: String
) : PagingSource<Int, CollectionItem>() {

    private var totalCollectionsCount = 0
    override fun getRefreshKey(state: PagingState<Int, CollectionItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CollectionItem> {
        val page = params.key ?: 1
        return try {
            val collectionsResponse = kinopoiskApi.getTopPopularAll(
                page = page,
                type = type,
                apiKey = context.getString(R.string.API_KEY)
                )
            totalCollectionsCount += collectionsResponse.items.size
            val movies = collectionsResponse.items
            LoadResult.Page(
                data = movies,
                nextKey = if (totalCollectionsCount == collectionsResponse.total) null else page + 1,
                prevKey = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(throwable = e)
        }
    }
}