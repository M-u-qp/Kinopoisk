package com.example.kinopoisk.presentation.search_filter.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.kinopoisk.presentation.search_filter.SearchFilterViewModel
import kotlin.math.roundToInt

@Composable
fun RatingSlider(
    viewModel: SearchFilterViewModel
) {
    var startRatingPosition by remember { mutableFloatStateOf(1f) }
    var endRatingPosition by remember { mutableFloatStateOf(10f) }

    Column {
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
            valueRange = 1f..10f,
            steps = 9,
            onValueChangeFinished = {
                viewModel.updateRatingSlider(
                    startRatingPosition.roundToInt().toFloat()..endRatingPosition.roundToInt()
                        .toFloat()
                )
            }
        )

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
            valueRange = 1f..10f,
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