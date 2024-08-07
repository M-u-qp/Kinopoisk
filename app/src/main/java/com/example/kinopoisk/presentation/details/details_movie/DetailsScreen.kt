package com.example.kinopoisk.presentation.details.details_movie

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import com.example.kinopoisk.R
import com.example.kinopoisk.domain.model.GalleryItem
import com.example.kinopoisk.domain.model.SeasonsItem
import com.example.kinopoisk.domain.model.SimilarItem
import com.example.kinopoisk.domain.model.Staff
import com.example.kinopoisk.presentation.Dimens
import com.example.kinopoisk.presentation.Dimens.ExtraSmallPadding2
import com.example.kinopoisk.presentation.Dimens.LargePadding1
import com.example.kinopoisk.presentation.Dimens.LazyVerticalGridHeight
import com.example.kinopoisk.presentation.Dimens.MediumFontSize2
import com.example.kinopoisk.presentation.Dimens.MediumPadding2
import com.example.kinopoisk.presentation.Dimens.MediumPadding3
import com.example.kinopoisk.presentation.Dimens.SmallPadding1
import com.example.kinopoisk.presentation.common.TitleCollection
import com.example.kinopoisk.presentation.common.TitleCommon
import com.example.kinopoisk.presentation.details.details_movie.components.DialogAddMovieInCollections
import com.example.kinopoisk.presentation.details.details_movie.components.GalleryMovie
import com.example.kinopoisk.presentation.details.details_movie.components.MovieDetailsCard
import com.example.kinopoisk.presentation.details.details_movie.components.SimilarMovieCard
import com.example.kinopoisk.presentation.details.details_movie.components.StaffCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun DetailsScreen(
    movieId: Int,
    viewModel: DetailsViewModel = hiltViewModel(),
    event: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit,
    navigateToStaffInfo: (Int) -> Unit,
    navigateToAllStaff: (List<Staff>) -> Unit,
    navigateToAllMovies: (List<SimilarItem>) -> Unit,
    galleryMovieStill: LazyPagingItems<GalleryItem>,
    navigateToDetails: (Int) -> Unit,
    navigateToAllGallery: (Int) -> Unit,
    navigateToAllSeasons: (List<SeasonsItem>) -> Unit
) {

    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value

    //Получить список всех коллекций
    LaunchedEffect(key1 = true) {
        withContext(Dispatchers.IO) {
            viewModel.getAllCollection()
        }
    }

    //Получить сезоны сериала
    LaunchedEffect(state.movie) {
        withContext(Dispatchers.IO) {
            if (state.movie != null) {
                viewModel.getSerialSeasons(movieId)
            }
        }
    }

    //Получить фильм из сети по кинопоиск айди
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
        event(DetailsEvent.AutoAddMovieInInteresting(movie))
    }

    //Получить похожие фильмы
    LaunchedEffect(key1 = true) {
        withContext(Dispatchers.IO) {
            viewModel.getSimilarMovies(movieId)
        }
    }

    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState
    ) {
        item {
            state.movie?.let { movie ->
                MovieDetailsCard(
                    movie = movie,
                    onLikeClick = { event(DetailsEvent.FavoriteMovie(movie)) },
                    onBookmarkClick = { event(DetailsEvent.ReadyToViewMovie(movie)) },
                    onViewedClick = { event(DetailsEvent.Viewed(movie)) },
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
                    onBackClick = navigateUp,
                    state = state
                )

                Column(
                    modifier = Modifier
                        .padding(top = MediumPadding3)
                        .padding(horizontal = MediumPadding2)
                ) {

                    //Диалог со списком коллекций для выбора пользователем
                    if (state.showDialogForCollections) {
                        DialogAddMovieInCollections(
                            state = state,
                            event = event
                        )
                    }
                    //Короткое описание фильма
                    if (movie.shortDescription != null) {
                        Text(
                            text = movie.shortDescription,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = MediumFontSize2
                            ),
                            color = colorResource(id = R.color.black_text)
                        )
                    }
                    //Основное описание фильма
                    if (movie.description != null) {
                        Text(
                            modifier = Modifier.padding(top = MediumPadding3),
                            text = movie.description,
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
        }
        //Сезоны сериала
        if (state.showSerialSeasons) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = LargePadding1, start = MediumPadding2)
                ) {
                    val titleName = stringResource(id = R.string.Seasons_and_episodes)
                    TitleCollection(
                        nameCollection = titleName,
                        onClick = { navigateToAllSeasons(state.serialSeasons) }
                    )
                    var episodesCount = 0
                    state.serialSeasons.forEach { seasons ->
                        episodesCount += seasons.episodes.size
                    }
                    Text(
                        text = "${state.serialSeasons.size} сезон, $episodesCount серий",
                        style = MaterialTheme.typography.displaySmall.copy(
                            fontSize = Dimens.SmallFontSize2,
                            color = colorResource(id = R.color.gray_text)
                        )
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
            ) {
                val filmStars = stringResource(id = R.string.Film_stars)
                TitleCommon(
                    nameTitle = filmStars,
                    varParam = state.listActors.size.toString(),
                    onClick = { navigateToAllStaff(state.listActors) }
                )
                LazyHorizontalGrid(
                    modifier = Modifier
                        .padding(top = SmallPadding1)
                        .fillMaxWidth()
                        .heightIn(max = LazyVerticalGridHeight),
                    rows = GridCells.Fixed(4),
                    horizontalArrangement = Arrangement.spacedBy(ExtraSmallPadding2),
                    verticalArrangement = Arrangement.spacedBy(ExtraSmallPadding2)
                ) {
                    items(state.listActors) { staff ->
                        StaffCard(
                            staff = staff,
                            onClick = { staff.staffId?.let { navigateToStaffInfo(it) } }
                        )
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
            ) {
                val filmOtherStaff = stringResource(id = R.string.Other_staff)
                TitleCommon(
                    nameTitle = filmOtherStaff,
                    varParam = state.listOtherStaff.size.toString(),
                    onClick = { navigateToAllStaff(state.listOtherStaff) }
                )
                LazyHorizontalGrid(
                    modifier = Modifier
                        .padding(top = SmallPadding1)
                        .fillMaxWidth()
                        .heightIn(max = LazyVerticalGridHeight),
                    rows = GridCells.Fixed(4),
                    horizontalArrangement = Arrangement.spacedBy(ExtraSmallPadding2),
                    verticalArrangement = Arrangement.spacedBy(ExtraSmallPadding2)
                ) {
                    items(state.listOtherStaff) { staff ->
                        StaffCard(
                            staff = staff,
                            onClick = { staff.staffId?.let { navigateToStaffInfo(it) } }
                        )
                    }
                }
            }
        }
        //Галерея
        item {
            Column(
                modifier = Modifier
                    .padding(top = LargePadding1, start = MediumPadding2)
                    .fillMaxWidth()
            ) {
                GalleryMovie(
                    images = galleryMovieStill,
                    navigateToAllGallery = { navigateToAllGallery(movieId) },
                    state = state,
                    viewModel = viewModel
                )
            }
        }
        //Похожие фильмы
        item {
            if (state.similarMovies.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .padding(top = LargePadding1, start = MediumPadding2)
                        .fillMaxWidth()
                ) {
                    val similarMovies = stringResource(id = R.string.Similar_movies)
                    TitleCommon(
                        nameTitle = similarMovies,
                        varParam = state.similarMovies.size.toString(),
                        onClick = { navigateToAllMovies(state.similarMovies) }
                    )
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(ExtraSmallPadding2)
                    ) {
                        items(state.similarMovies) { similarItem ->
                            similarItem.filmId?.let {
                                SimilarMovieCard(
                                    item = similarItem,
                                    onClick = { navigateToDetails(it) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


