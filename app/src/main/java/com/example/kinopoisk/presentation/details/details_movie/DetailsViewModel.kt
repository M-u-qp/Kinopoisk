package com.example.kinopoisk.presentation.details.details_movie

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.domain.model.Movie
import com.example.kinopoisk.domain.usecases.collections.CollectionsUseCases
import com.example.kinopoisk.domain.usecases.movies.MoviesUseCases
import com.example.kinopoisk.domain.usecases.staff.StaffUseCases
import com.example.kinopoisk.domain.utils.Resource
import com.example.kinopoisk.presentation.common.TitleCollectionsDB
import com.example.kinopoisk.presentation.common.TypeSearchFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.toList
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

    //Получение всех коллекций в БД
    suspend fun getAllCollection() {
        collectionsUseCases.getCollectionsInDB().collect {
            _state.value = _state.value.copy(
                listCollections = it
            )
        }
    }

    //Создание новой коллекции
    suspend fun addCollectionInDB(collectionDB: CollectionDB) {
        var addCollection = false
        if (state.value.listCollections.isNotEmpty()) {
            state.value.listCollections.forEach { listCollections ->
                if (listCollections.nameCollection == collectionDB.nameCollection) {
                    addCollection = true
                    updateShowErrorDialog(show = true, collectionName = collectionDB.nameCollection)
                    return@forEach
                }
            }
            if (!addCollection) {
                collectionsUseCases.addCollection(collectionDB)
            }
        }
    }

    //Получение всех фильмов в БД
    suspend fun getAllMoviesInDB() {
        moviesUseCases.getAllMoviesInDB().collect {
            _state.value = _state.value.copy(listMovie = it)
        }
    }

    suspend fun getCollectionSize(nameCollection: String) {
        collectionsUseCases.getCollectionInDB(nameCollection).collect {
            _state.value = _state.value.copy(
                listCollectionsAndSize = _state.value.listCollectionsAndSize.toMutableMap()
                    .apply { put(nameCollection, it) })
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

            //Клик по иконке Троеточие ( выбор своей коллекции из списка в диалоге или создать новую )
            is DetailsEvent.AddMovieInCollection -> {
                val selectedCollection = state.value.selectedCollection
                if (selectedCollection.isNotEmpty()) {
                    selectedCollection.forEach { selectedCollectionValue ->
                        addDeleteMovieInDB(event.movie, selectedCollectionValue)
                    }
                    _state.value = _state.value.copy(
                        selectedCollection = emptyList(),
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

    fun updateSelectedCollectionAdd(selectedCollection: String) {
        _state.value =
            _state.value.copy(selectedCollection = _state.value.selectedCollection.toMutableList()
                .apply { add(selectedCollection) })
    }

    fun updateSelectedCollectionDelete(selectedCollection: String) {
        _state.value =
            _state.value.copy(selectedCollection = _state.value.selectedCollection.toMutableList()
                .apply { remove(selectedCollection) })
    }

    fun updateSelectedCollectionDeleteAll() {
        _state.value =
            _state.value.copy(selectedCollection = _state.value.selectedCollection.toMutableList()
                .apply { removeAll(state.value.selectedCollection) })
    }

    fun updateShowDialogForCreateCollection(show: Boolean) {
        _state.value = _state.value.copy(showDialogForCreateCollection = show)
    }

    fun updateShowErrorDialog(show: Boolean, collectionName: String) {
        _state.value = _state.value.copy(
            showErrorDialog = show,
            errorCollectionName = collectionName
        )
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
    suspend fun getMovie(movieId: Int) {
        if (state.value.movie == null) {
            _state.value = _state.value.copy(loadingMovie = true)
            when (val movie = moviesUseCases.getMovie(movieId)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        loadingMovie = false,
                        movie = movie.data,
                        errorMovies = null
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        loadingMovie = false,
                        errorMovies = movie.exception.toString()
                    )
                }

                else -> Unit
            }
        }
    }

    //Сезоны сериала
    suspend fun getSerialSeasons(id: Int) {
        if (state.value.serialSeasons.isEmpty() && state.value.movie != null) {
            state.value.movie?.let { movie ->
                if (movie.type == TypeSearchFilter.TV_SERIES.name) {
                    when (val seasons = moviesUseCases.getSerialSeasons(id = id)) {
                        is Resource.Success -> {
                            val serialName = movie.nameRu ?: movie.nameEn ?: ""
                            seasons.data?.let {
                                _state.value = _state.value.copy(
                                    serialSeasons = it.map { serial ->
                                        serial.copy(serialName = serialName)
                                    },
                                    showSerialSeasons = true,
                                    errorSeasons = null
                                )
                            }
                        }

                        is Resource.Error -> {
                            _state.value =
                                _state.value.copy(errorSeasons = seasons.exception.toString())
                        }

                        else -> Unit
                    }
                }
            }
        }
    }

    //Список актеров и т.п.
    suspend fun getListStaff(filmId: Int) {
        if (state.value.listActors.isEmpty()) {
            when (val listStaff = staffUseCases.getListStaff(filmId)) {
                is Resource.Success -> {
                    val actors =
                        listStaff.data?.filter { staff -> staff.professionKey == "ACTOR" }
                    val otherStaff =
                        listStaff.data?.filter { staff -> staff.professionKey != "ACTOR" }
                            ?.distinctBy { staff -> staff.nameRu ?: staff.nameEn }
                    _state.value = _state.value.copy(
                        listActors = actors ?: emptyList(),
                        listOtherStaff = otherStaff ?: emptyList(),
                        errorStaff = null
                    )
                }

                is Resource.Error -> {
                    _state.value =
                        _state.value.copy(errorStaff = listStaff.exception.toString())
                }

                else -> Unit
            }
        }
    }

    //Галерея фильма
    fun getGalleryMovie(id: Int, type: String) {
        viewModelScope.launch {
            if (state.value.galleryItem.toList().isEmpty()) {
                val galleryStill =
                    moviesUseCases.galleryMovie(id = id, type = type).cachedIn(viewModelScope)
                _state.value = _state.value.copy(galleryItem = galleryStill)
            }
        }
    }

    //Похожие фильмы
    suspend fun getSimilarMovies(id: Int) {
        if (state.value.similarMovies.isEmpty()) {
            when (val listSimilarMovies = collectionsUseCases.getSimilarMovies(id = id)) {
                is Resource.Success -> {
                    listSimilarMovies.data?.let { listMovies ->
                        _state.value = _state.value.copy(
                            similarMovies = listMovies,
                            errorSimilar = null
                        )
                    }
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        errorSimilar = listSimilarMovies.exception.toString()
                    )
                }

                else -> Unit
            }
        }
    }
}