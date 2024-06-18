package com.example.kinopoisk.presentation.details.details_movie.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.Dimens.MediumPadding2

@Composable
fun MovieDetailsCard(
    movie: Movie,
    onLikeClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onShareClick: () -> Unit,
    onBrowsingClick: () -> Unit,
    onDotsClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp / 1.2f

    Box(
        modifier = Modifier.fillMaxSize().height(screenHeight)
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(context).data(movie.posterUrl).build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        //Градиент постера
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            val gradient = Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color.Black),
                startY = 0f,
                endY = constraints.maxHeight.toFloat()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(gradient)
            )
        }

        IconButton(
            modifier = Modifier.padding(top = MediumPadding2, start = MediumPadding2),
            onClick = onBackClick
        ) {
            Icon(
                modifier = Modifier.scale(3f),
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                tint = colorResource(id = R.color.body_icon)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Row {
                //Рейтинг
                Text(
                    text = (movie.ratingKinopoisk ?: "").toString() + " ",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = Dimens.SmallFontSize2
                    ),
                    color = colorResource(id = R.color.body_icon)
                )
                //Название
                Text(
                    text = (movie.nameRu ?: movie.nameEn).toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = Dimens.SmallFontSize2
                    ),
                    color = colorResource(id = R.color.body_icon)
                )
            }
            Row {
                //Год
                Text(
                    text = movie.year.toString() + ", ",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = Dimens.SmallFontSize2
                    ),
                    color = colorResource(id = R.color.body_icon)
                )
                //Жанр
                Text(
                    text = movie.genres.joinToString(separator = "", limit = 1) { it.genre },
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = Dimens.SmallFontSize2
                    ),
                    color = colorResource(id = R.color.body_icon),
                )
            }
            Row {
                //Страна
                Text(
                    text = movie.countries.joinToString(
                        separator = "",
                        limit = 1
                    ) { it.country } + ", ",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = Dimens.SmallFontSize2
                    ),
                    color = colorResource(id = R.color.body_icon))
                //Продолжительность
                Text(
                    text = (movie.filmLength ?: "").toString() + " мин, ",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = Dimens.SmallFontSize2
                    ),
                    color = colorResource(id = R.color.body_icon)
                )
                //Возрастное ограничение
                Text(
                    text = (movie.ratingAgeLimits ?: "").toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = Dimens.SmallFontSize2
                    ),
                    color = colorResource(id = R.color.body_icon)
                )
            }

            DetailsTopBar(
                onLikeClick = onLikeClick,
                onBookmarkClick = onBookmarkClick,
                onShareClick = onShareClick,
                onBrowsingClick = onBrowsingClick,
                onDotsClick = onDotsClick
            )
        }
    }
}
