package com.example.kinopoisk.presentation.details.details_movie.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding3
import com.example.kinopoisk.presentation.Dimens.SmallPadding1
import com.example.kinopoisk.presentation.common.normalizeTitleMovie

@Composable
fun MovieCardDialogItem(
    modifier: Modifier = Modifier,
    movie: Movie
) {
    val context = LocalContext.current

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier) {
            //Постер фильма
            AsyncImage(
                modifier = Modifier
                    .size(
                        width = Dimens.MovieCardSearchWidth,
                        height = Dimens.MovieCardSearchHeight
                    )
                    .clip(MaterialTheme.shapes.medium),
                model = ImageRequest.Builder(context).data(movie.posterUrl).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            if (movie.ratingKinopoisk != 0.0 && movie.ratingKinopoisk.toString() != "null") {
                Box(
                    modifier = Modifier
                        .padding(top = Dimens.ExtraSmallPadding1, start = Dimens.ExtraSmallPadding1)
                        .size(
                            width = Dimens.RatingMovieCardDialogWidth,
                            height = Dimens.RatingMovieCardDialogHeight
                        )
                        .background(
                            color = Color.White,
                            shape = ShapeDefaults.Medium
                        )
                        .align(Alignment.TopStart),
                    contentAlignment = Alignment.Center
                ) {
                    //Рейтинг Кинопоиск
                    Text(
                        text = (movie.ratingKinopoisk).toString(),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = Dimens.ExtraSmallFontSize1,
                            fontWeight = FontWeight.Medium,
                            color = colorResource(id = R.color.black_text)
                        )
                    )
                }
            }
        }
        Column(modifier = Modifier.padding(start = SmallPadding1)) {
            //Название фильма
            val nameMovie = normalizeTitleMovie(movie.nameRu ?: movie.nameEn ?: "")
            Text(
                text = nameMovie,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = Dimens.SmallFontSize1
                ),
                color = colorResource(id = R.color.black_text),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(modifier = Modifier.padding(top = ExtraSmallPadding3)) {
                //Год выхода фильма
                Text(
                    text = movie.year.toString() + ", ",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = Dimens.SmallFontSize2
                    ),
                    color = colorResource(id = R.color.gray_text),
                )
                //Жанр фильма
                Text(
                    text = movie.genres.joinToString(separator = "", limit = 1) { it.genre },
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = Dimens.SmallFontSize2
                    ),
                    color = colorResource(id = R.color.gray_text),
                )
            }

        }
    }
}