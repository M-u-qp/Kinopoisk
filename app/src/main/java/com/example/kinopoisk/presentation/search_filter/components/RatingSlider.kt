package com.example.kinopoisk.presentation.search_filter.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.search_filter.SearchFilterViewModel
import kotlin.math.roundToInt

@Composable
fun RatingSlider(
    modifier: Modifier = Modifier,
    viewModel: SearchFilterViewModel
) {
    var startRatingPosition by remember { mutableFloatStateOf(0f) }
    var endRatingPosition by remember { mutableFloatStateOf(10f) }

    Column(modifier = modifier) {
        Slider(
            value = startRatingPosition,
            onValueChange = { newValue ->
                startRatingPosition = newValue
                if (startRatingPosition > endRatingPosition) {
                    startRatingPosition = endRatingPosition
                }
                viewModel.updateRatingSlider(
                    startRatingPosition.roundToInt().toFloat()..endRatingPosition.roundToInt()
                        .toFloat()
                )
            },
            valueRange = 0f..10f,
            steps = 9,
            onValueChangeFinished = {
                viewModel.updateRatingSlider(
                    startRatingPosition.roundToInt().toFloat()..endRatingPosition.roundToInt()
                        .toFloat()
                )
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "0",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = colorResource(id = R.color.gray_text),
                    fontSize = Dimens.SmallFontSize1,
                ))
            Text(text = "10",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = colorResource(id = R.color.gray_text),
                    fontSize = Dimens.SmallFontSize1,
                ))
        }
        Slider(
            value = endRatingPosition,
            onValueChange = { newValue ->
                endRatingPosition = newValue
                if (endRatingPosition < startRatingPosition) {
                    endRatingPosition = startRatingPosition
                }
                viewModel.updateRatingSlider(
                    startRatingPosition.roundToInt().toFloat()..endRatingPosition.roundToInt()
                        .toFloat()
                )
            },
            valueRange = 0f..10f,
            steps = 9,
            onValueChangeFinished = {
                viewModel.updateRatingSlider(
                    startRatingPosition.roundToInt().toFloat()..endRatingPosition.roundToInt()
                        .toFloat()
                )
            }
        )
    }
}