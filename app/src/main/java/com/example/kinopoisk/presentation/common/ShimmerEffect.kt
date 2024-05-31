package com.example.kinopoisk.presentation.common

import android.annotation.SuppressLint
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.shimmerEffect() = composed {
    val transition = rememberInfiniteTransition(label = "")
    val alpha = transition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    ).value
    background(color = colorResource(id = R.color.shimmer).copy(alpha = alpha))
}

@Composable
fun MovieCardCollectionShimmerEffect(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
    ) {
        Box(modifier = modifier) {
            Box(
                modifier = modifier
                    .size(width = Dimens.MovieCardSizeWidth, height = Dimens.MovieCardSizeHeight)
                    .clip(MaterialTheme.shapes.medium)
                    .shimmerEffect(),
            )
        }
    }
}

@Composable
fun MovieCardSearchShimmerEffect(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
    ) {
        Box(modifier = modifier) {
            Box(
                modifier = modifier
                    .size(width = Dimens.MovieCardSearchWidth, height = Dimens.MovieCardSearchHeight)
                    .clip(MaterialTheme.shapes.medium)
                    .shimmerEffect(),
            )
        }
    }
}