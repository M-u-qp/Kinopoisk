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
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding2
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding3
import com.example.kinopoisk.presentation.common.EmptyScreen
import com.example.kinopoisk.presentation.common.MovieCardCollectionShimmerEffect
import com.example.kinopoisk.presentation.common.TitleCollection

@Composable
fun MoviesListCollection(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<CollectionItem>,
    onClick: (Int) -> Unit,
    collectionName: String,
    navigateToAllMovies: (List<CollectionItem>) -> Unit
) {
    val handlePagingResult = handlePagingResult(movies = movies)
    if (handlePagingResult) {
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
                items(count = movies.itemCount) { index ->
                    movies[index]?.let {
                        MovieCardCollection(
                            nameMovie = it.nameRu ?: it.nameEn ?: it.nameOriginal ?: "",
                            genreMovie = it.genres,
                            posterUrl = it.posterUrl,
                            rating = it.ratingKinopoisk,
                            onClick = { onClick(it.kinopoiskId) }
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun handlePagingResult(
    movies: LazyPagingItems<CollectionItem>
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