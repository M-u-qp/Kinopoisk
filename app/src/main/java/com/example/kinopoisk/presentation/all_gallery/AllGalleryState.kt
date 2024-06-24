package com.example.kinopoisk.presentation.all_gallery

import androidx.paging.PagingData
import com.example.kinopoisk.domain.model.GalleryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class AllGalleryState(
    val imageGalleryStill: Flow<PagingData<GalleryItem>> = flowOf(),
    val imageGalleryShooting: Flow<PagingData<GalleryItem>> = flowOf(),
    val imageGalleryFanArt: Flow<PagingData<GalleryItem>> = flowOf(),
    val imageGalleryConcept: Flow<PagingData<GalleryItem>> = flowOf(),
    var showGalleryDialog: Boolean = false
)
