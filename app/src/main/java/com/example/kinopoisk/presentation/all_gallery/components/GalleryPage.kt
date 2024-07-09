package com.example.kinopoisk.presentation.all_gallery.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.kinopoisk.domain.model.GalleryItem
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding2
import com.example.kinopoisk.presentation.all_gallery.AllGalleryState
import com.example.kinopoisk.presentation.all_gallery.AllGalleryViewModel
import com.example.kinopoisk.presentation.details.details_movie.components.GalleryMovieItem
import com.example.kinopoisk.presentation.details.details_movie.components.handlePagingResult

@Composable
fun GalleryPage(
    movieId: Int,
    tab: String,
    index: Int,
    state: AllGalleryState,
    viewModel: AllGalleryViewModel
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        when (index) {
            0 -> {
                val images = state.imageGalleryStill.collectAsLazyPagingItems()
                HandleGalleryPage(
                    movieId = movieId,
                    tab = tab,
                    viewModel = viewModel,
                    images = images,
                    state = state
                )
            }

            1 -> {
                val images = state.imageGalleryShooting.collectAsLazyPagingItems()
                HandleGalleryPage(
                    movieId = movieId,
                    tab = tab,
                    viewModel = viewModel,
                    images = images,
                    state = state
                )
            }

            2 -> {
                val images = state.imageGalleryFanArt.collectAsLazyPagingItems()
                HandleGalleryPage(
                    movieId = movieId,
                    tab = tab,
                    viewModel = viewModel,
                    images = images,
                    state = state
                )
            }

            3 -> {
                val images = state.imageGalleryConcept.collectAsLazyPagingItems()
                HandleGalleryPage(
                    movieId = movieId,
                    tab = tab,
                    viewModel = viewModel,
                    images = images,
                    state = state
                )
            }

            else -> Unit
        }
    }
}

@Composable
private fun HandleGalleryPage(
    movieId: Int,
    tab: String,
    viewModel: AllGalleryViewModel,
    images: LazyPagingItems<GalleryItem>,
    state: AllGalleryState
) {
    viewModel.getGalleryMovie(id = movieId, type = tab)
    val handlePagingResult = handlePagingResult(images = images)
    if (handlePagingResult) {

        if (viewModel.state.value.showGalleryDialog) {
            GalleryDialog(
                listImages = images.itemSnapshotList.items,
                viewModel = viewModel,
                state = state
            )
        }

        if (images.itemSnapshotList.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = Dimens.MediumPadding2),
                verticalArrangement = Arrangement.spacedBy(ExtraSmallPadding2),
                horizontalArrangement = Arrangement.spacedBy(ExtraSmallPadding2),
            ) {
                items(count = images.itemCount) { index ->
                    images[index]?.let {
                        GalleryMovieItem(
                            galleryItem = it,
                            onClick = {
                                viewModel.updateVisibleGalleryDialog(true)
                                viewModel.updateCurrentImage(index)
                            }
                        )
                    }
                }
            }
        }
    }
}