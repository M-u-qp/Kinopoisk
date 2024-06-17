package com.example.kinopoisk.presentation.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.example.kinopoisk.R

@Composable
fun DetailsTopBar(
    onLikeClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit,
    onBrowsingClick: () -> Unit,
    onDotsClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {

        IconButton(onClick = onLikeClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_like_border),
                contentDescription = null,
                tint = colorResource(id = R.color.body_icon)
            )
        }
        IconButton(onClick = onBookmarkClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_bookmark_border),
                contentDescription = null,
                tint = colorResource(id = R.color.body_icon)
            )
        }
        IconButton(onClick = onShareClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_share),
                contentDescription = null,
                tint = colorResource(id = R.color.body_icon)
            )
        }
        IconButton(onClick = onBrowsingClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_explore),
                contentDescription = null,
                tint = colorResource(id = R.color.body_icon)
            )
        }
        IconButton(onClick = onDotsClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_dots),
                contentDescription = null,
                tint = colorResource(id = R.color.body_icon)
            )
        }
    }
}
