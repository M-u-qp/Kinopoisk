package com.example.kinopoisk.presentation.all_seasons

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.SeasonsItem
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.Dimens.MediumPadding1
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.Dimens.SmallFontSize1
import com.example.kinopoisk.presentation.all_seasons.components.SeasonPage
import com.example.kinopoisk.presentation.all_seasons.components.TabSeasons
import com.example.kinopoisk.presentation.common.NavigateUpButton

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AllSeasonsScreen(
    seasonsItem: List<SeasonsItem>,
    navigateUp: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        val pagerState = rememberPagerState(initialPage = 0) { seasonsItem.size }
        val scope = rememberCoroutineScope()
        val tabs = remember { seasonsItem.indices.toList() }

        val serialName = seasonsItem.first().serialName
        NavigateUpButton(
            modifier = Modifier.padding(start = MediumPadding2),
            navigateUp = navigateUp,
            text = serialName
        )

        Row(
            modifier = Modifier.padding(top = MediumPadding1),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(horizontal = MediumPadding2),
                text = stringResource(id = R.string.Seasons),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = colorResource(id = R.color.black_text),
                    fontSize = SmallFontSize1,
                    fontWeight = FontWeight.Medium
                ))
            TabSeasons(pagerState = pagerState, tabs = tabs, scope = scope)
        }

        HorizontalPager(
            modifier = Modifier
                .padding(top = Dimens.SmallPadding1)
                .weight(0.8f),
            state = pagerState
        ) { index ->

            SeasonPage(
                modifier = Modifier.padding(start = MediumPadding2),
                episodes = seasonsItem[index].episodes
            )
        }
    }
}