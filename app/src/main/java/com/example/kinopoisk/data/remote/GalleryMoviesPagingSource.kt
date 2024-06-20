package com.example.kinopoisk.data.remote

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.GalleryItem

class GalleryMoviesPagingSource(
    private val kinopoiskApi: KinopoiskApi,
    private val context: Context,
    private val id: Int,
    private val type: String
) : PagingSource<Int, GalleryItem>() {

    private var totalCollectionsCount = 0
    override fun getRefreshKey(state: PagingState<Int, GalleryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryItem> {
        val page = params.key ?: 1
        return try {
            val collectionsResponse = kinopoiskApi.getGalleryMovie(
                page = page,
                apiKey = context.getString(R.string.API_KEY),
                id = id,
                type = type
            )
            totalCollectionsCount += collectionsResponse.items.size
                val images = collectionsResponse.items
                LoadResult.Page(
                    data = images,
                    nextKey = if (totalCollectionsCount == collectionsResponse.total) null else page + 1,
                    prevKey = null
                )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(throwable = e)
        }
    }
}