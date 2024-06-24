package com.example.kinopoisk.presentation.details.details_movie.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kinopoisk.domain.model.GalleryItem
import com.example.kinopoisk.presentation.Dimens.GalleryImageHeight
import com.example.kinopoisk.presentation.Dimens.GalleryImageWidth

@Composable
fun GalleryMovieItem(
    modifier: Modifier = Modifier,
    galleryItem: GalleryItem,
    onClick: () -> Unit
) {

    val context = LocalContext.current

    Box(
        modifier = modifier.clickable { onClick() }
    ) {
        AsyncImage(
            modifier = Modifier
                .size(width = GalleryImageWidth, height = GalleryImageHeight)
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(context).data(galleryItem.imageUrl).build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}