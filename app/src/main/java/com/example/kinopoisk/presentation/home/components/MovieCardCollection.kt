package com.example.kinopoisk.presentation.home.components

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
import com.example.kinopoisk.domain.model.Item
import com.example.kinopoisk.presentation.Dimens.ExtraSmallFontSize1
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding1
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding2
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding3
import com.example.kinopoisk.presentation.Dimens.MovieCardSizeHeight
import com.example.kinopoisk.presentation.Dimens.MovieCardSizeWidth
import com.example.kinopoisk.presentation.Dimens.RatingMovieHeight
import com.example.kinopoisk.presentation.Dimens.RatingMovieWidth
import com.example.kinopoisk.presentation.Dimens.SmallFontSize1
import com.example.kinopoisk.presentation.Dimens.SmallFontSize2

@Composable
fun MovieCardCollection(
    modifier: Modifier = Modifier,
    item: Item,
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
                    .size(width = MovieCardSizeWidth, height = MovieCardSizeHeight)
                    .clip(MaterialTheme.shapes.medium),
                model = ImageRequest.Builder(context).data(item.posterUrl).build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = modifier
                    .size(RatingMovieWidth, RatingMovieHeight)
                    .padding(ExtraSmallPadding1)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        shape = ShapeDefaults.ExtraSmall
                    )
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ) {
                //Рейтинг Кинопоиск
                Text(
                    text = (item.ratingKinopoisk).toString(),
                    color = colorResource(id = R.color.white),

                    fontSize = ExtraSmallFontSize1,
                    fontWeight = FontWeight.Medium

                )
            }
        }
        //Название фильма
        val nameMovie = normalizeTitleMovie(item)
        Text(
            modifier = modifier.padding(top = ExtraSmallPadding2),
            text = nameMovie,
            style = MaterialTheme.typography.labelMedium.copy(
                fontSize = SmallFontSize1
            ),
            color = colorResource(id = R.color.black_text),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        //Жанр фильма
        Text(
            modifier = modifier.padding(top = ExtraSmallPadding3),
            text = item.genres.joinToString(separator = "", limit = 1) { it.genre },
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = SmallFontSize2
            ),
            color = colorResource(id = R.color.gray_text),
        )
    }
}

//Перенос слов на вторую строку, если не убирается название фильма
private fun normalizeTitleMovie(item: Item): String {
    val text = item.nameRu ?: item.nameEn ?: ""
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

//@Preview(showBackground = true)
//@Composable
//fun PreviewMovieCard() {
//    MovieCard(item = Item(
//        genres = listOf(Genre("драма")),
//        nameRu = "великийинепов непревзойденный"
//    )){}
//}
