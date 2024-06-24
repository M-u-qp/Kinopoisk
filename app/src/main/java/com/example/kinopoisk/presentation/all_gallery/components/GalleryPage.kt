package com.example.kinopoisk.presentation.all_gallery.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.kinopoisk.domain.model.GalleryItem
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.all_gallery.AllGalleryState
import com.example.kinopoisk.presentation.all_gallery.AllGalleryViewModel
import com.example.kinopoisk.presentation.details.details_movie.components.GalleryMovieItem
import com.example.kinopoisk.presentation.details.details_movie.components.handlePagingResult

@Composable
fun GalleryPage(
    movieId: Int,
    tab: String,
    index: Int,
    state: AllGalleryState
) {

    val viewModel: AllGalleryViewModel = hiltViewModel()
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
                    images = images
                )
            }

            1 -> {
                val images = state.imageGalleryShooting.collectAsLazyPagingItems()
                HandleGalleryPage(
                    movieId = movieId,
                    tab = tab,
                    viewModel = viewModel,
                    images = images
                )
            }

            2 -> {
                val images = state.imageGalleryFanArt.collectAsLazyPagingItems()
                HandleGalleryPage(
                    movieId = movieId,
                    tab = tab,
                    viewModel = viewModel,
                    images = images
                )
            }

            3 -> {
                val images = state.imageGalleryConcept.collectAsLazyPagingItems()
                HandleGalleryPage(
                    movieId = movieId,
                    tab = tab,
                    viewModel = viewModel,
                    images = images
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
    images: LazyPagingItems<GalleryItem>
) {
    viewModel.getGalleryMovie(id = movieId, type = tab)
    val handlePagingResult = handlePagingResult(images = images)
    if (handlePagingResult) {
        if (images.itemSnapshotList.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Dimens.ExtraSmallPadding2)
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