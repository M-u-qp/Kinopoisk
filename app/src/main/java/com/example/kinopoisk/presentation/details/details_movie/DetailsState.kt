package com.example.kinopoisk.presentation.details.details_movie

import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.domain.model.GalleryItem
import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.domain.model.Staff
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class DetailsState(
    val movie: Movie? = null,
    val loadingMovie: Boolean = false,
    val error: String? = null,

    val listMovie: List<Movie?> = emptyList(),
    val loadingCollections: Boolean = false,
    val listCollections: List<CollectionDB> = emptyList(),
    var showDialogForCollections: Boolean = false,
    var selectedCollection: String = "",
    var movieViewed: Boolean = false,

    val listActors: List<Staff> = emptyList(),
    val listOtherStaff: List<Staff> = emptyList(),

    val movieGalleryStill: Flow<PagingData<GalleryItem>> = flowOf(PagingData.empty())
)