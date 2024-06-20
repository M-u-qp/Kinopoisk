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
import com.example.kinopoisk.domain.model.CollectionItem
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
    item: CollectionItem,
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
            if (item.ratingKinopoisk != 0.0) {
                Box(
                    modifier = modifier
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
                        text = (item.ratingKinopoisk).toString(),
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
        val nameMovie = normalizeTitleMovie(item.nameRu ?: item.nameEn ?: "")
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
