package com.example.kinopoisk.presentation.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.common.KinopoiskButton
import com.example.kinopoisk.presentation.common.KinopoiskTextButton
import com.example.kinopoisk.presentation.onboarding.components.OnBoardingPage
import com.example.kinopoisk.presentation.onboarding.components.PageIndicator
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    event: (OnBoardingEvent) -> Unit
) {

    Column(modifier = Modifier.fillMaxSize()) {
        val pagerState = rememberPagerState(initialPage = 0) { pages.size }
        val buttonState = remember {
            derivedStateOf {
                when (pagerState.currentPage) {
                    0 -> listOf("", "Дальше")
                    1 -> listOf("Назад", "Дальше")
                    2 -> listOf("Назад", "Начать")
                    else -> listOf("", "")
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = MediumPadding2),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                text = stringResource(id = R.string.app_name),
                color = colorResource(id = R.color.black_text)
            )
            KinopoiskTextButton(
                text = stringResource(id = R.string.Skip),
                onClick = {
                    event(OnBoardingEvent.SaveAppEntry)
                }
            )
        }

        HorizontalPager(
            modifier = Modifier.weight(0.8f),
            state = pagerState
        ) { index ->
            OnBoardingPage(page = pages[index])
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = MediumPadding2)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PageIndicator(
                modifier = Modifier.padding(start = 4.dp),
                pageSize = pages.size,
                selectedPage = pagerState.currentPage
            )
            Spacer(modifier = Modifier.weight(1f))
            val scope = rememberCoroutineScope()
            if (buttonState.value[0].isNotEmpty()) {
                KinopoiskTextButton(
                    text = buttonState.value[0],
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                        }
                    }
                )
            }
            KinopoiskButton(
                text = buttonState.value[1],
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage == 2) {
                            event(OnBoardingEvent.SaveAppEntry)
                        } else {
                            pagerState.animateScrollToPage(
                                page = pagerState.currentPage + 1
                            )
                        }
                    }
                }
            )
        }
    }
}