package com.example.kinopoisk.presentation.details.details_staff

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoisk.domain.usecases.movies.MoviesUseCases
import com.example.kinopoisk.domain.usecases.staff.StaffUseCases
import com.example.kinopoisk.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaffViewModel @Inject constructor(
    private val staffUseCases: StaffUseCases,
    private val moviesUseCases: MoviesUseCases
) : ViewModel() {

    private val _state = mutableStateOf(StaffState())
    val state: State<StaffState> = _state

    fun getStaffInfo(id: Int) {
        viewModelScope.launch {
            when (val staff = staffUseCases.getStaff(id)) {
                is Resource.Success -> {
                    val filterBestMovies = staff.data?.films!!.filter {
                        (it.rating?.toDouble() ?: 0.0) >= 8.0
                    }.distinctBy { it.nameRu ?: it.nameEn }
                    _state.value = _state.value.copy(
                        staffInfo = staff.data,
                        filterBestMovies = filterBestMovies,
                        isLoadingMovies = true,
                        errorStaff = null
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        errorStaff = staff.exception.toString()
                    )
                }

                else -> Unit
            }
        }
    }

    fun getBestFilms(movieId: Int) {
        if (state.value.filterBestMovies.isNotEmpty()) {
            viewModelScope.launch {
                when (val movie = moviesUseCases.getMovie(movieId)) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            listBestMovies = _state.value.listBestMovies.apply { addAll(listOf(movie.data)) },
                            errorMovie = null
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            errorMovie = movie.exception.toString()
                        )
                    }

                    else -> Unit
                }
            }
        }
    }


    fun updateVisibleDialogFilmography(show: Boolean) {
        _state.value = _state.value.copy(isShowDialogFilmography = show)
    }

    fun updateIsLoadingMovies(loading: Boolean) {
        _state.value = _state.value.copy(isLoadingMovies = loading)
    }
}