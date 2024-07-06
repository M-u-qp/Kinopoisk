package com.example.kinopoisk.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.Genre
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
import com.example.kinopoisk.presentation.common.normalizeTitleMovie

@Composable
fun MovieCardCollection(
    modifier: Modifier = Modifier,
    nameMovie: String,
    genreMovie:List<Genre>,
    posterUrl: String?,
    rating: Double?,
    onClick: (() -> Unit)? = null
) {
    val context = LocalContext.current
    var showFullTitle by remember { mutableStateOf(false) }

        if (showFullTitle) {
            Dialog(onDismissRequest = { showFullTitle = false }) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White,
                            shape = MaterialTheme.shapes.medium
                        ),
                    text = nameMovie,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = SmallFontSize1,
                        color = colorResource(id = R.color.black_text)
                    )
                )
            }
        }
        Column(
            modifier = modifier
                .clickable { onClick?.invoke() }
        ) {
            Box(modifier = Modifier) {
                //Постер фильма
                AsyncImage(
                    modifier = Modifier
                        .size(width = MovieCardSizeWidth, height = MovieCardSizeHeight)
                        .clip(MaterialTheme.shapes.medium),
                    model = ImageRequest.Builder(context).data(posterUrl).build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                if (rating != 0.0 && rating != null) {
                    Box(
                        modifier = Modifier
                            .padding(top = ExtraSmallPadding1, end = ExtraSmallPadding1)
                            .size(width = RatingMovieWidth, height = RatingMovieHeight)
                            .background(
                                MaterialTheme.colorScheme.primary,
                                shape = ShapeDefaults.Medium
                            )
                            .align(Alignment.TopEnd),
                        contentAlignment = Alignment.Center
                    ) {
                        //Рейтинг Кинопоиск
                        Text(
                            text = (rating).toString(),
                            color = colorResource(id = R.color.white),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = ExtraSmallFontSize1,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
            //Название фильма
            val normalizeNameMovie = normalizeTitleMovie(nameMovie)
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = ExtraSmallPadding2)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                showFullTitle = true
                            },
                            onTap = {
                                showFullTitle = false
                            }
                        )
                    },
                text = normalizeNameMovie,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontSize = SmallFontSize1,
                    color = colorResource(id = R.color.black_text)
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            //Жанр фильма
            Text(
                modifier = Modifier.padding(top = ExtraSmallPadding3),
                text = " " + genreMovie.joinToString(separator = "", limit = 1) { it.genre },
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = SmallFontSize2
                ),
                color = colorResource(id = R.color.gray_text),
            )
        }
}
