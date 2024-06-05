package com.example.kinopoisk.presentation.bookmark.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.presentation.Dimens

@Composable
fun MovieCardDatabase(
    modifier: Modifier = Modifier,
    movie: Movie,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    Row(
        modifier = modifier
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(modifier = modifier) {
            //Постер фильма
            AsyncImage(
                modifier = modifier
                    .size(
                        width = Dimens.MovieCardSearchWidth,
                        height = Dimens.MovieCardSearchHeight
                    )
                    .clip(MaterialTheme.shapes.medium),
                model = ImageRequest.Builder(context).data(movie.posterUrl).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = modifier
                    .size(Dimens.RatingMovieWidth, Dimens.RatingMovieHeight)
                    .padding(Dimens.ExtraSmallPadding1)
                    .background(
                        MaterialTheme.colorScheme.onPrimary,
                        shape = ShapeDefaults.ExtraSmall
                    )
                    .align(Alignment.TopStart),
                contentAlignment = Alignment.Center
            ) {
                //Рейтинг Кинопоиск
                Text(
                    text = movie.ratingKinopoisk.toString(),
                    color = colorResource(id = R.color.black_text),

                    fontSize = Dimens.ExtraSmallFontSize1,
                    fontWeight = FontWeight.Medium

                )
            }
        }

        Column(
            modifier = modifier.padding(start = Dimens.SmallPadding1)
        ) {

            //Название фильма
            Text(
                text = movie.let { it.nameRu ?: it.nameEn }.toString(),
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = Dimens.SmallFontSize1
                ),
                color = colorResource(id = R.color.black_text),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = modifier.padding(top = Dimens.ExtraSmallPadding4),
                verticalAlignment = Alignment.CenterVertically
            ) {
                //Год выхода фильма
                Text(
                    text = (movie.year).toString() + ", ",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = Dimens.SmallFontSize2
                    ),
                    color = colorResource(id = R.color.gray_text),
                )
                //Жанр фильма
                Text(
                    text = movie.genres.joinToString(separator = "", limit = 1) { it.genre  },
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = Dimens.SmallFontSize2
                    ),
                    color = colorResource(id = R.color.gray_text),
                )
            }

        }
    }
}