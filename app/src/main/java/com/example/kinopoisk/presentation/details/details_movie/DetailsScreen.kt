package com.example.kinopoisk.presentation.details.details_movie

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.kinopoisk.R
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding2
import com.example.kinopoisk.presentation.Dimens.LargePadding1
import com.example.kinopoisk.presentation.Dimens.MediumFontSize2
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.Dimens.MediumPadding3
import com.example.kinopoisk.presentation.Dimens.SmallPadding1
import com.example.kinopoisk.presentation.common.TitleCommon
import com.example.kinopoisk.presentation.details.details_movie.components.DialogAddMovieInCollections
import com.example.kinopoisk.presentation.details.details_movie.components.MovieDetailsCard
import com.example.kinopoisk.presentation.details.details_movie.components.StaffCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun DetailsScreen(
    movieId: Int,
    viewModel: DetailsViewModel = hiltViewModel(),
    event: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit
) {

    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value

    //Получить фильм с сети по кинопоиск айди
    LaunchedEffect(key1 = true) {
        withContext(Dispatchers.IO) {
            viewModel.getMovie(movieId)
        }
    }
    //Получить все фильмы из базы
    LaunchedEffect(key1 = true) {
        withContext(Dispatchers.IO) {
            viewModel.getAllMoviesInDB()
        }
    }
    //Получить список актеров и т.п.
    LaunchedEffect(key1 = true) {
        withContext(Dispatchers.IO) {
            viewModel.getListStaff(movieId)
        }
    }
    //Добавить фильм в просмотренные в авторежиме
    state.movie?.let { movie ->
        event(DetailsEvent.AutoAddMovieInViewed(movie))
    }

    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier,
        state = lazyListState
    ) {
        item {
            state.movie?.let { movie ->
                MovieDetailsCard(
                    movie = movie,
                    onLikeClick = { event(DetailsEvent.FavoriteMovie(movie)) },
                    onBookmarkClick = { event(DetailsEvent.ReadyToViewMovie(movie)) },
                    onShareClick = {
                        Intent(Intent.ACTION_SEND).also {
                            it.putExtra(Intent.EXTRA_TEXT, movie.webUrl)
                            it.type = "text/plain"
                            if (it.resolveActivity(context.packageManager) != null) {
                                context.startActivity(it)
                            }
                        }
                    },
                    onBrowsingClick = {
                        Intent(Intent.ACTION_VIEW).also {
                            it.data = Uri.parse(movie.webUrl)
                            if (it.resolveActivity(context.packageManager) != null) {
                                context.startActivity(it)
                            }
                        }
                    },
                    onDotsClick = { event(DetailsEvent.AddMovieInCollection(movie)) },
                    onBackClick = navigateUp
                )

                Column(
                    modifier = Modifier
                        .padding(top = MediumPadding3)
                        .padding(horizontal = MediumPadding2)
                ) {

                    //Диалог со списком коллекций для выбора пользователем
                    if (state.showDialogForCollections) {
                        DialogAddMovieInCollections(state = state, event = event)
                    }
                    //Короткое описание фильма
                    Text(
                        text = movie.shortDescription ?: "",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = MediumFontSize2
                        ),
                        color = colorResource(id = R.color.black_text)
                    )
                    //Основное описание фильма
                    Text(
                        modifier = Modifier.padding(top = MediumPadding3),
                        text = movie.description ?: "",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            fontSize = MediumFontSize2
                        ),
                        color = colorResource(id = R.color.black_text),
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
        //Список актеров
        item {
            Column(
                modifier = Modifier
                    .padding(top = LargePadding1, start = MediumPadding2)
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                val filmStars = stringResource(id = R.string.Film_stars)
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TitleCommon(
                        nameTitle = filmStars,
                        count = state.listActors.size,
                        onClick = {}
                    )
                }
                LazyHorizontalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = SmallPadding1),
                    rows = GridCells.Fixed(4),
                    horizontalArrangement = Arrangement.spacedBy(ExtraSmallPadding2),
                    verticalArrangement = Arrangement.spacedBy(ExtraSmallPadding2)
                ) {
                    items(state.listActors) {
                        StaffCard(staff = it)
                    }
                }
            }
        }

        //Список остального персонала
        item {
            Column(
                modifier = Modifier
                    .padding(top = LargePadding1, start = MediumPadding2)
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                val filmOtherStaff = stringResource(id = R.string.Other_staff)
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TitleCommon(
                        nameTitle = filmOtherStaff,
                        count = state.listOtherStaff.size,
                        onClick = {}
                    )
                }
                LazyHorizontalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = SmallPadding1),
                    rows = GridCells.Fixed(4),
                    horizontalArrangement = Arrangement.spacedBy(ExtraSmallPadding2),
                    verticalArrangement = Arrangement.spacedBy(ExtraSmallPadding2)
                ) {
                    items(state.listOtherStaff) {
                        StaffCard(staff = it)
                    }
                }
            }
        }
    }
}

