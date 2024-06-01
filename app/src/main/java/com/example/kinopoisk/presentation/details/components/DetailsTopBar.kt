package com.example.kinopoisk.presentation.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens.IconBackSize
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.Dimens.MoviePosterHeight
import com.example.kinopoisk.presentation.Dimens.SmallPadding1

@Composable
fun DetailsTopBar(
    onLikeClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit,
    onBrowsingClick: () -> Unit,
    onBackClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(MoviePosterHeight)
            .padding(start = MediumPadding2, top = SmallPadding1)
            .statusBarsPadding(),
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onBackClick) {
                Icon(
                    modifier = Modifier.size(IconBackSize),
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    tint = colorResource(id = R.color.black_text)
                )
            }

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
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_unviewed),
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
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dots),
                        contentDescription = null,
                        tint = colorResource(id = R.color.body_icon)
                    )
                }
            }


        }

    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewDetailsTopBar() {
//    DetailsTopBar(
//        onLikeClick = { /*TODO*/ },
//        onBookmarkClick = { /*TODO*/ },
//        onShareClick = { /*TODO*/ },
//        onBackClick = {},
//        onBrowsingClick = {}
//    )
//}