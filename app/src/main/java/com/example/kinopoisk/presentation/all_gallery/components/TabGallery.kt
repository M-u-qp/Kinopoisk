package com.example.kinopoisk.presentation.all_gallery.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.common.TypeGalleryRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabGallery(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    tabs: List<TypeGalleryRequest>,
    scope: CoroutineScope
) {

    ScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = pagerState.currentPage,
        divider = { Spacer(modifier = Modifier.width(Dimens.ExtraSmallPadding2)) },
        edgePadding = Dimens.ExtraSmallPadding2,
        indicator = { tabPositions ->
            Box(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                    .fillMaxWidth()
                    .border(
                        BorderStroke(
                            color = colorResource(id = R.color.black), width = 1.dp
                        ),
                        shape = MaterialTheme.shapes.medium
                    )
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = MaterialTheme.shapes.medium
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = Dimens.ExtraSmallPadding4, vertical = Dimens.ExtraSmallPadding3),
                    text = tabs[pagerState.currentPage].value,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = Dimens.MediumFontSize3,
                        color = Color.White
                    )
                )
            }
        },
        containerColor = Color.Transparent
    ) {
        tabs.forEachIndexed { index, text ->
            Tab(
                modifier = Modifier.padding(horizontal = Dimens.ExtraSmallPadding2),
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.scrollToPage(index)
                    }
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            BorderStroke(
                                color = colorResource(id = R.color.black), width = 1.dp
                            ),
                            shape = MaterialTheme.shapes.medium
                        )
                        .background(
                            color = Color.Transparent,
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(horizontal = Dimens.ExtraSmallPadding4, vertical = Dimens.ExtraSmallPadding3),
                        text = text.value,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = Dimens.MediumFontSize3
                        )
                    )
                }
            }
        }
    }
}