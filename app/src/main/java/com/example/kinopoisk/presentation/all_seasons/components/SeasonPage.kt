package com.example.kinopoisk.presentation.all_seasons.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.SeasonsEpisode
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.Dimens.MediumPadding1
import com.example.kinopoisk.presentation.Dimens.SmallFontSize1
import com.example.kinopoisk.presentation.Dimens.SmallFontSize3

@Composable
fun SeasonPage(
    modifier: Modifier = Modifier,
    episodes: List<SeasonsEpisode>
) {

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(MediumPadding1)

    ) {
        item {
            Text(
                modifier = Modifier.padding(top = MediumPadding1),
                text = "${episodes.first().seasonNumber} сезон, ${episodes.size} серий",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = colorResource(id = R.color.gray_text),
                    fontSize = SmallFontSize1,
                    fontWeight = FontWeight.Medium
                )
            )
        }

        items(episodes) { episode ->
            val expandedState = remember { mutableStateOf(false) }
            Column(
                modifier = Modifier.clickable { expandedState.value = true }
            ) {
                (episode.nameRu ?: episode.nameEn)?.let { episodeName ->
                    Text(
                        text = "${episode.episodeNumber} серия. $episodeName",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = colorResource(id = R.color.black_text),
                            fontSize = SmallFontSize1,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
                episode.releaseDate?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = colorResource(id = R.color.gray_text),
                            fontSize = SmallFontSize3,
                        )
                    )
                }
            }
            if (expandedState.value) {
                (episode.synopsis)?.let { synopsis ->
                    Text(
                        modifier = Modifier
                            .padding(end = Dimens.MediumPadding2)
                            .clickable { expandedState.value = false },
                        text = synopsis,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = colorResource(id = R.color.black_text),
                            fontSize = SmallFontSize1,
                        )
                    )
                }
            }
        }
    }
}