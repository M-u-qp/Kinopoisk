package com.example.kinopoisk.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.kinopoisk.domain.model.Item
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding2
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding3

@Composable
fun MoviesListCollection(
    modifier: Modifier = Modifier,
    movies: LazyPagingItems<Item>,
    onClick: (Item) -> Unit
) {
    val handlePagingResult = handlePagingResult(movies = movies)
    if (handlePagingResult) {
        LazyRow(
            modifier = modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(ExtraSmallPadding2),
            contentPadding = PaddingValues(all = ExtraSmallPadding3)
        ) {
            items(count = movies.itemCount){ index ->
                movies[index]?.let {
                    MovieCardCollection(item = it, onClick = {onClick(it)})
                }
            }
        }
    }

}

@Composable
fun handlePagingResult(
    movies: LazyPagingItems<Item>
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
            EmptyScreen()
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