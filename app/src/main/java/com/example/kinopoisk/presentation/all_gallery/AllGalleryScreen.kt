package com.example.kinopoisk.presentation.all_gallery

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.Dimens.SmallPadding1
import com.example.kinopoisk.presentation.all_gallery.components.GalleryPage
import com.example.kinopoisk.presentation.all_gallery.components.TabGallery
import com.example.kinopoisk.presentation.common.NavigateUpButton
import com.example.kinopoisk.presentation.common.TypeGalleryRequest

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AllGalleryScreen(
    state: AllGalleryState,
    movieId: Int,
    navigateUp: () -> Unit,
) {

    val viewModel: AllGalleryViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        val pagerState = rememberPagerState(initialPage = 0) { TypeGalleryRequest.entries.size }
        val scope = rememberCoroutineScope()
        val tabs = remember {
            listOf(
                TypeGalleryRequest.STILL,
                TypeGalleryRequest.SHOOTING,
                TypeGalleryRequest.FAN_ART,
                TypeGalleryRequest.CONCEPT
            )
        }
        NavigateUpButton(
            modifier = Modifier.padding(start = MediumPadding2),
            navigateUp = navigateUp,
            text = TypeGalleryRequest.entries[pagerState.currentPage].value
        )

        TabGallery(
            modifier = Modifier.padding(start = MediumPadding2),
            pagerState = pagerState,
            tabs = tabs,
            scope = scope
        )

        HorizontalPager(
            modifier = Modifier
                .padding(top = SmallPadding1)
                .weight(0.8f),
            state = pagerState
        ) { index ->
            GalleryPage(
                movieId = movieId,
                tab = tabs[index].name,
                index = index,
                state = state,
                viewModel = viewModel
            )
        }
    }
}