package com.example.kinopoisk.presentation.details.details_movie.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.GalleryItem
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.common.EmptyScreen
import com.example.kinopoisk.presentation.common.TitleCommon

@Composable
fun GalleryMovie(
    images: LazyPagingItems<GalleryItem>
) {
    val handlePagingResult = handlePagingResult(images = images)
    if (handlePagingResult) {

        if (images.itemSnapshotList.isNotEmpty()) {
            Column {
                val gallery = stringResource(id = R.string.Gallery)
                TitleCommon(
                    nameTitle = gallery,
                    varParam = images.itemCount.toString(),
                    onClick = {}
                )

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.ExtraSmallPadding2)
                ) {
                    items(count = images.itemCount) { index ->
                        images[index]?.let {
                            GalleryMovieItem(galleryItem = it)
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun handlePagingResult(
    images: LazyPagingItems<GalleryItem>
): Boolean {
    val loadState = images.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }
    return when {
        loadState.refresh is LoadState.Loading -> {
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