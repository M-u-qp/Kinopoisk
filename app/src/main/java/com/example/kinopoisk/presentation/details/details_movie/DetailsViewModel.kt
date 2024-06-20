package com.example.kinopoisk.presentation.details.details_movie

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.kinopoisk.domain.model.GalleryItem
import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.domain.usecases.collections.CollectionsUseCases
import com.example.kinopoisk.domain.usecases.movies.MoviesUseCases
import com.example.kinopoisk.domain.usecases.staff.StaffUseCases
import com.example.kinopoisk.domain.utils.Resource
import com.example.kinopoisk.presentation.common.TitleCollectionsDB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val moviesUseCases: MoviesUseCases,
    private val collectionsUseCases: CollectionsUseCases,
    private val staffUseCases: StaffUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsState())
    val state: StateFlow<DetailsState> = _state

    var sideEffect by mutableStateOf<String?>(null)
        private set

    init {
        viewModelScope.launch {
            getAllCollection()
        }
    }

    //Получение всех коллекций в БД
    private suspend fun getAllCollection() {
        collectionsUseCases.getCollectionsInDB().collect {
            _state.value = _state.value.copy(
                listCollections = it
            )
        }
    }

    //Получение всех фильмов в БД
    suspend fun getAllMoviesInDB() {
        moviesUseCases.getAllMoviesInDB().collect {
            _state.value = _state.value.copy(listMovie = it)
        }
    }


    fun onEvent(event: DetailsEvent) {
        when (event) {
            //Клик по иконке Закладка ( добавить/удалить фильм из Хочу посмотреть )
            is DetailsEvent.ReadyToViewMovie -> {
                addDeleteMovieInDB(event.movie, TitleCollectionsDB.READY_TO_VIEW.value)
            }

            //Клик по иконке Лайк ( добавить/удалить фильм из Понравилось )
            is DetailsEvent.FavoriteMovie -> {
                addDeleteMovieInDB(event.movie, TitleCollectionsDB.FAVORITE.value)
            }

            //Клик по иконке Троеточие ( выбор своей коллекции из списка в диалоге )
            is DetailsEvent.AddMovieInCollection -> {
                val selectedCollection = state.value.selectedCollection
                if (selectedCollection.isNotEmpty()) {
                    addDeleteMovieInDB(event.movie, selectedCollection)
                    _state.value = _state.value.copy(
                        selectedCollection = "",
                        showDialogForCollections = false
                    )
                } else {
                    _state.value = _state.value.copy(showDialogForCollections = true)
                }
            }
            //Ивент авто добавления фильма в просмотренные
            is DetailsEvent.AutoAddMovieInViewed -> {
                autoAddMovieInViewed(event.movie, TitleCollectionsDB.VIEWED.value)
            }

            is DetailsEvent.RemoveSideEffect -> {
                sideEffect = null
            }
        }
    }

    //Добавление фильма в коллекцию "Просмотрено" в БД
    private fun autoAddMovieInViewed(movie: Movie, collectionName: String) {
        _state.value = _state.value.copy(movieViewed = false)
        viewModelScope.launch {
            val movieForDB = movie.copy(collectionName = collectionName)
            if (state.value.listMovie.isEmpty()) {
                moviesUseCases.upsertMovie(movieForDB)
            } else {
                var shouldAddMovie = true
                state.value.listMovie.forEach { existingMovie ->
                    if (movie.kinopoiskId == existingMovie?.kinopoiskId && existingMovie.collectionName == collectionName) {
                        _state.value = _state.value.copy(movieViewed = true)
                        shouldAddMovie = false
                    }
                }
                if (shouldAddMovie) {
                    moviesUseCases.upsertMovie(movieForDB)
                }
            }
        }
    }

    //Добавление/удаление фильмов из коллекции в БД
    private fun addDeleteMovieInDB(movie: Movie, collectionName: String) {
        viewModelScope.launch {
            val movieForDB = movie.copy(collectionName = collectionName)
            if (state.value.listMovie.isEmpty()) {
                upsertMovie(movieForDB)
            } else {
                var shouldAddMovie = true
                state.value.listMovie.forEach { existingMovie ->
                    if (movie.kinopoiskId == existingMovie?.kinopoiskId && existingMovie.collectionName == collectionName) {
                        deleteMovie(existingMovie.id)
                        shouldAddMovie = false
                    }
                }
                if (shouldAddMovie) {
                    upsertMovie(movieForDB)
                }
            }
        }
    }

    fun updateShowDialog(isVisible: Boolean) {
        _state.value = _state.value.copy(showDialogForCollections = isVisible)
    }

    fun updateSelectedCollection(selectedCollection: String) {
        _state.value = _state.value.copy(selectedCollection = selectedCollection)
    }

    private suspend fun deleteMovie(id: Int) {
        moviesUseCases.deleteMovieById(id = id)
        sideEffect = "Фильм удален"
    }

    private suspend fun upsertMovie(movie: Movie) {
        moviesUseCases.upsertMovie(movie = movie)
        sideEffect = "Фильм сохранен"
    }

    //Детальное инфо о фильме
    fun getMovie(movieId: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loadingMovie = true)
            when (val movie = moviesUseCases.getMovie(movieId)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        loadingMovie = false,
                        movie = movie.data,
                        error = null
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        loadingMovie = false,
                        error = movie.exception.toString()
                    )
                }

                else -> Unit
            }
        }
    }

    //Список актеров и т.п.
    fun getListStaff(filmId: Int) {
        viewModelScope.launch {
            when (val listStaff = staffUseCases.getListStaff(filmId)) {
                is Resource.Success -> {
                    val actors = listStaff.data?.filter { staff -> staff.professionKey == "ACTOR" }
                    val otherStaff =
                        listStaff.data?.filter { staff -> staff.professionKey != "ACTOR" }
                            ?.distinctBy { staff -> staff.nameRu ?: staff.nameEn }
                    _state.value = _state.value.copy(
                        listActors = actors ?: emptyList(),
                        listOtherStaff = otherStaff ?: emptyList()
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(error = listStaff.exception.toString())
                }

                else -> Unit
            }
        }
    }

    //Галерея фильма
    fun getGalleryMovie(id: Int, type: String): Flow<PagingData<GalleryItem>> {
        val result = moviesUseCases.galleryMovie(id = id, type = type).cachedIn(viewModelScope)
        _state.value = _state.value.copy(movieGalleryStill = result)
        return state.value.movieGalleryStill
    }
}