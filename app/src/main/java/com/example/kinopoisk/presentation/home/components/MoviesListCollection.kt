package com.example.kinopoisk.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.kinopoisk.domain.model.CollectionItem
import com.example.kinopoisk.domain.model.FilterItem
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding2
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding3
import com.example.kinopoisk.presentation.common.EmptyScreen
import com.example.kinopoisk.presentation.common.MovieCardCollectionShimmerEffect
import com.example.kinopoisk.presentation.common.TitleCollection

@Composable
fun MoviesListCollection(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<*>,
    onClick: (Int) -> Unit,
    collectionName: String,
    collectionType: String,
    navigateToAllMovies: (List<*>) -> Unit
) {
    val handlePagingResult = handlePagingResult(movies = movies)
    if (handlePagingResult) {
        when(collectionType) {
            "FilterItem" -> {
                val movieItems = movies.itemSnapshotList.filterIsInstance<FilterItem>()
                Column {
                    TitleCollection(
                        nameCollection = collectionName,
                        onClick = { navigateToAllMovies(movies.itemSnapshotList.items) }
                    )
                    LazyRow(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(ExtraSmallPadding2),
                        contentPadding = PaddingValues(all = ExtraSmallPadding3)
                    ) {
                        items(count = movieItems.size) { index ->
                            MovieCardCollection(
                                nameMovie = movieItems[index].nameRu ?: movieItems[index].nameEn
                                ?: movieItems[index].nameOriginal ?: "",
                                genreMovie = movieItems[index].genres,
                                posterUrl = movieItems[index].posterUrl,
                                rating = movieItems[index].ratingKinopoisk,
                                onClick = { onClick(movieItems[index].kinopoiskId) }
                            )
                        }
                    }
                }
            }
            else -> {
                val movieItems = movies.itemSnapshotList.filterIsInstance<CollectionItem>()
                Column {
                    TitleCollection(
                        nameCollection = collectionName,
                        onClick = { navigateToAllMovies(movies.itemSnapshotList.items) }
                    )
                    LazyRow(
                        modifier = modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(ExtraSmallPadding2),
                        contentPadding = PaddingValues(all = ExtraSmallPadding3)
                    ) {
                        items(count = movieItems.size) { index ->
                            MovieCardCollection(
                                nameMovie = movieItems[index].nameRu ?: movieItems[index].nameEn
                                ?: movieItems[index].nameOriginal ?: "",
                                genreMovie = movieItems[index].genres,
                                posterUrl = movieItems[index].posterUrl,
                                rating = movieItems[index].ratingKinopoisk,
                                onClick = { onClick(movieItems[index].kinopoiskId) }
                            )
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun handlePagingResult(
    movies: LazyPagingItems<*>
): Boolean {
    val loadState = movies.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }
    return when {
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }

        error != null -> {
            EmptyScreen(error = error)
            false
        }

        else -> {
            true
        }
    }
}

@Composable
private fun ShimmerEffect() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(ExtraSmallPadding2)
    ) {
        repeat(10) {
            MovieCardCollectionShimmerEffect()
        }
    }
}