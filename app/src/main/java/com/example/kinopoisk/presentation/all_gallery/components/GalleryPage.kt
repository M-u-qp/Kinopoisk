package com.example.kinopoisk.presentation.all_gallery.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.kinopoisk.domain.model.GalleryItem
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.details.details_movie.components.GalleryMovieItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GalleryPage(
    pagerState: PagerState,
    images: List<GalleryItem>
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        if (images.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(Dimens.ExtraSmallPadding2)
            ) {
                items(count = images.size) { index ->
                    GalleryMovieItem(galleryItem = images[index])
                }
            }
        }
    }
}