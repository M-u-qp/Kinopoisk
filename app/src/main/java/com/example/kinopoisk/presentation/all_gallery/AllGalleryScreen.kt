package com.example.kinopoisk.presentation.all_gallery

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.GalleryItem
import com.example.kinopoisk.presentation.Dimens.MediumFontSize3
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.Dimens.SmallPadding1
import com.example.kinopoisk.presentation.all_gallery.components.GalleryPage
import com.example.kinopoisk.presentation.all_gallery.components.TabGallery
import com.example.kinopoisk.presentation.common.TypeGalleryRequest

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AllGalleryScreen(
    images: List<GalleryItem>,
    navigateUp: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = MediumPadding2)
            .statusBarsPadding()
    ) {

        val pagerState = rememberPagerState(initialPage = 0) { TypeGalleryRequest.entries.size }
        val scope = rememberCoroutineScope()
        val tabs = remember {
            listOf(
                TypeGalleryRequest.STILL.value,
                TypeGalleryRequest.SHOOTING.value,
                TypeGalleryRequest.FAN_ART.value,
                TypeGalleryRequest.CONCEPT.value
            )
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {

            IconButton(onClick = navigateUp) {
                Icon(
                    painter = painterResource(
                        id = R.drawable.ic_back_arrow
                    ),
                    contentDescription = null
                )
            }

            Text(
                modifier = Modifier.align(Alignment.Center),
                text = TypeGalleryRequest.entries[pagerState.currentPage].value,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = MediumFontSize3,
                    color = colorResource(id = R.color.black_text)
                )
            )

        }
        TabGallery(pagerState = pagerState, tabs = tabs, scope = scope)

        HorizontalPager(
            modifier = Modifier
                .padding(top = SmallPadding1)
                .weight(0.8f),
            state = pagerState
        ) { index ->
            GalleryPage(
                pagerState = pagerState,
                images = images
            )
        }
    }
}