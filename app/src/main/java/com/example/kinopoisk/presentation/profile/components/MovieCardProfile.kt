package com.example.kinopoisk.presentation.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
fun MovieCardProfile(
    modifier: Modifier = Modifier,
    movie: Movie,
    onClick: (() -> Unit)? = null
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .clickable { onClick?.invoke() }
    ) {
        Box(modifier = modifier) {
            //Постер фильма
            AsyncImage(
                modifier = modifier
                    .size(width = Dimens.MovieCardSizeWidth, height = Dimens.MovieCardSizeHeight)
                    .clip(MaterialTheme.shapes.medium),
                model = ImageRequest.Builder(context).data(movie.posterUrl).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            if (movie.ratingKinopoisk != 0.0) {
                Box(
                    modifier = modifier
                        .padding(top = Dimens.ExtraSmallPadding1, end = Dimens.ExtraSmallPadding1)
                        .size(width = Dimens.RatingMovieWidth, height = Dimens.RatingMovieHeight)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            shape = ShapeDefaults.Medium
                        )
                        .align(Alignment.TopEnd),
                    contentAlignment = Alignment.Center
                ) {
                    //Рейтинг Кинопоиск
                    Text(
                        text = (movie.ratingKinopoisk).toString(),
                        color = colorResource(id = R.color.white),
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = Dimens.ExtraSmallFontSize1,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
        }
        //Название фильма
        val nameMovie = normalizeTitleMovie(movie)
        Text(
            modifier = modifier.padding(top = Dimens.ExtraSmallPadding2),
            text = nameMovie,
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = Dimens.SmallFontSize1
            ),
            color = colorResource(id = R.color.black_text),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        //Жанр фильма
        Text(
            modifier = modifier.padding(top = Dimens.ExtraSmallPadding3),
            text = movie.genres.joinToString(separator = "", limit = 1) { it.genre },
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = Dimens.SmallFontSize2
            ),
            color = colorResource(id = R.color.gray_text),
        )
    }
}

//Перенос слов на вторую строку, если не убирается название фильма
private fun normalizeTitleMovie(movie: Movie): String {
    val text = movie.nameRu ?: movie.nameEn ?: ""
    val words = text.split(" ")
    val lines = mutableListOf<String>()
    var currentLine = ""
    for ((index, word) in words.withIndex()) {
        if (index == 0 && word.length >= 13) {
            currentLine = word.substring(0, 13)
            lines.add(currentLine)
            currentLine = ""
        } else if (currentLine.length + word.length + 1 <= 13) {
            currentLine += " $word"
        } else {
            lines.add(currentLine)
            currentLine = if (word.length >= 10) {
                word.substring(0, 10) + "..."
            } else {
                word
            }

        }
    }
    if (currentLine.isNotEmpty()) {
        lines.add(currentLine)
    }
    return lines.joinToString("\n")
}