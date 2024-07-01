package com.example.kinopoisk.presentation.details.details_movie

import androidx.paging.PagingData
import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.domain.model.GalleryItem
import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.domain.model.SimilarItem
import com.example.kinopoisk.domain.model.Staff
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class DetailsState(
    val movie: Movie? = null,
    val loadingMovie: Boolean = false,
    val errorMovies: String? = null,

    val listMovie: List<Movie?> = emptyList(),
    val loadingCollections: Boolean = false,
    val listCollections: List<CollectionDB> = emptyList(),
    var showDialogForCollections: Boolean = false,
    var selectedCollection: List<String> = emptyList(),
    var movieViewed: Boolean = false,
    var showDialogForCreateCollection: Boolean = false,
    val listCollectionsAndSize: Map<String, List<Movie?>> = mutableMapOf(),
    var showErrorDialog: Boolean = false,
    var errorCollectionName: String = "",

    val listActors: List<Staff> = emptyList(),
    val listOtherStaff: List<Staff> = emptyList(),
    val errorStaff: String? = null,

    val similarMovies: List<SimilarItem> = emptyList(),
    val errorSimilar: String? = null,

    val galleryItem: Flow<PagingData<GalleryItem>> = flowOf()
)