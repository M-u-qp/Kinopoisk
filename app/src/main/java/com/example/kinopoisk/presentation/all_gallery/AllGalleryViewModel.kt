package com.example.kinopoisk.presentation.all_gallery

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kinopoisk.domain.usecases.movies.MoviesUseCases
import com.example.kinopoisk.presentation.common.TypeGalleryRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllGalleryViewModel @Inject constructor(
    private val moviesUseCases: MoviesUseCases
) : ViewModel() {


    private val _state = mutableStateOf(AllGalleryState())
    val state: MutableState<AllGalleryState> = _state

    fun getGalleryMovie(id: Int, type: String) {
        viewModelScope.launch {
            when (type) {
                TypeGalleryRequest.STILL.name -> {
                    if (state.value.imageGalleryStill.toList().isEmpty()) {
                        val images =
                            moviesUseCases.galleryMovie(id = id, type = type)
                                .cachedIn(viewModelScope)
                        _state.value = _state.value.copy(imageGalleryStill = images)
                    }
                }

                TypeGalleryRequest.SHOOTING.name -> {
                    if (state.value.imageGalleryShooting.toList().isEmpty()) {
                        val images =
                            moviesUseCases.galleryMovie(id = id, type = type)
                                .cachedIn(viewModelScope)
                        _state.value = _state.value.copy(imageGalleryShooting = images)
                    }
                }

                TypeGalleryRequest.FAN_ART.name -> {
                    if (state.value.imageGalleryFanArt.toList().isEmpty()) {
                        val images =
                            moviesUseCases.galleryMovie(id = id, type = type)
                                .cachedIn(viewModelScope)
                        _state.value = _state.value.copy(imageGalleryFanArt = images)
                    }
                }

                TypeGalleryRequest.CONCEPT.name -> {
                    if (state.value.imageGalleryConcept.toList().isEmpty()) {
                        val images =
                            moviesUseCases.galleryMovie(id = id, type = type)
                                .cachedIn(viewModelScope)
                        _state.value = _state.value.copy(imageGalleryConcept = images)
                    }
                }

                else -> Unit
            }

        }
    }

    fun updateVisibleGalleryDialog(show: Boolean) {
        _state.value = _state.value.copy(showGalleryDialog = show)
    }
    fun updateCurrentImage(index: Int) {
        _state.value = _state.value.copy(currentIndex = index)
    }
}