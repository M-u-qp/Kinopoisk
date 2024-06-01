package com.example.kinopoisk.presentation.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.Dimens.MoviePosterHeight

@Composable
fun MovieDetailsCard(
    movie: Movie
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(MoviePosterHeight)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(context).data(movie.posterUrl).build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize()
                .padding(bottom = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Row {
                //Рейтинг
                Text(text = (movie.ratingKinopoisk).toString() + " ",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = Dimens.SmallFontSize2
                    ),
                    color = colorResource(id = R.color.body_icon))
                //Название
                Text(text = movie.let { it.nameRu ?: movie.nameEn },
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = Dimens.SmallFontSize2
                    ),
                    color = colorResource(id = R.color.body_icon))
            }
            Row {
                //Год
                Text(text = movie.year.toString() + ", ",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = Dimens.SmallFontSize2
                    ),
                    color = colorResource(id = R.color.body_icon))
                //Жанр
                Text(text = movie.genres.joinToString(separator = "", limit = 1) { it.genre },
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = Dimens.SmallFontSize2
                    ),
                    color = colorResource(id = R.color.body_icon),)
            }
            Row {
                //Страна
                Text(text = movie.countries.joinToString(separator = "", limit = 1) { it.country } + ", ",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = Dimens.SmallFontSize2
                    ),
                    color = colorResource(id = R.color.body_icon))
                //Продолжительность
                Text(text = (movie.filmLength).toString() + " мин, ",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = Dimens.SmallFontSize2
                    ),
                    color = colorResource(id = R.color.body_icon))
                //Возрастное ограничение
                Text(text = movie.ratingAgeLimits,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = Dimens.SmallFontSize2
                    ),
                    color = colorResource(id = R.color.body_icon))
            }

        }
    }
}

//@Preview
//@Composable
//fun PreviewMovieDetailsCard() {
//    MovieDetailsCard(movie = Movie(
//        nameRu = "Топи",
//        ratingKinopoisk = 6.9,
//        genres = listOf(Genre("триллер, драма")),
//        countries = listOf(Country("Россия, Бельгия")),
//        filmLength = 55, year = 2021,
//        ratingAgeLimits = "18+"
//    ))
//}