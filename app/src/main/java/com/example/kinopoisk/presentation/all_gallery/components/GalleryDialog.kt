package com.example.kinopoisk.presentation.all_gallery.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kinopoisk.domain.model.GalleryItem
import com.example.kinopoisk.presentation.all_gallery.AllGalleryViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GalleryDialog(
    listImages: List<GalleryItem>,
    viewModel: AllGalleryViewModel
) {
    val context = LocalContext.current
    val pagerState = rememberPagerState(initialPage = 0) { listImages.size }
    val showDialog = remember { mutableStateOf(false) }

    Dialog(onDismissRequest = {
        showDialog.value = false
        viewModel.updateVisibleGalleryDialog(showDialog.value)
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .layout { measurable, constraints ->
                    val placeable = measurable.measure(constraints)
                    layout(placeable.width, placeable.height) {
                        placeable.placeRelative(0, 0)
                    }
                }
                .background(color = Color.Black.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
        HorizontalPager(modifier = Modifier.fillMaxSize(), state = pagerState) { index ->

                AsyncImage(
                    modifier = Modifier.fillMaxWidth(),
                    model = ImageRequest.Builder(context).data(listImages[index].imageUrl).build(),
                    contentDescription = null,

                    )
            }
        }
    }
}