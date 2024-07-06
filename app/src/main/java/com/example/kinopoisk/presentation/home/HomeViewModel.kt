package com.example.kinopoisk.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoisk.domain.model.CollectionDB
import com.example.kinopoisk.domain.usecases.collections.CollectionsUseCases
import com.example.kinopoisk.domain.usecases.movies.MoviesUseCases
import com.example.kinopoisk.domain.utils.Resource
import com.example.kinopoisk.presentation.common.TitleCollections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val collectionsUseCases: CollectionsUseCases,
    private val moviesUseCases: MoviesUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state

    init {
        viewModelScope.launch {
            getAllCollectionInDB()
        }
        getCountriesAndGenres()
    }

    //Получить список подборок из БД
    private suspend fun getAllCollectionInDB() {
        collectionsUseCases.getCollectionsInDB().collect { listCollections ->
            _state.value = _state.value.copy(listCollections = listCollections)
        }
    }

    //Добавить коллекцию в БД(при автоматическом первом создании "Нравится" и "Хочу посмотреть")
    suspend fun addCollectionInDB(collectionDB: CollectionDB) {
        collectionsUseCases.addCollection(collectionDB)
    }

    //Получить премьеры
    suspend fun getPremieres(year: Int, month: String) {
        if (state.value.premieres.isEmpty()) {
            when (val premieres = collectionsUseCases.getPremieres(year = year, month = month)) {
                is Resource.Success -> {
                    premieres.data?.let {
                        _state.value = _state.value.copy(
                            premieres = premieres.data,
                            errorPremieres = null
                        )
                    }
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        errorPremieres = premieres.exception.toString()
                    )
                }

                else -> Unit
            }


        }
    }

    //Получить различные подборки из сети
    fun getCollection(type: String) {
        when (type) {
            TitleCollections.TOP_POPULAR_MOVIES.name -> {
                if (state.value.popularMovies == null) {
                    val lazyResult = collectionsUseCases.getCollections(type = type)
                    _state.value = _state.value.copy(popularMovies = lazyResult)
                }
            }

            TitleCollections.POPULAR_SERIES.name -> {
                if (state.value.popularSerials == null) {
                    val lazyResult = collectionsUseCases.getCollections(type = type)
                    _state.value = _state.value.copy(popularSerials = lazyResult)
                }
            }
        }
    }

    //Получить список стран и жанров
    private fun getCountriesAndGenres() {
        if (state.value.countriesAndGenres == null) {
            viewModelScope.launch {
                when (val countriesAndGenres = moviesUseCases.getCountriesAndGenres()) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            countriesAndGenres = countriesAndGenres.data,
                            errorCountriesAndGenres = null
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            errorCountriesAndGenres = countriesAndGenres.exception.toString()
                        )
                    }

                    else -> Unit
                }
            }
        }
    }

    //Получить подборку по стране и жанру
    fun getDynamicCollection1() {
        if (state.value.dynamicMovies1 == null) {
            val randomCountry = state.value.countriesAndGenres?.countries?.random()
            val randomGenre = state.value.countriesAndGenres?.genres?.random()
            val lazyResult =
                randomCountry?.let { country ->
                    randomGenre?.let { genre ->
                        collectionsUseCases.getDynamicMovies(
                            listOf(country.id),
                            listOf(genre.id)
                        )
                    }
                }
            _state.value = _state.value.copy(
                dynamicMovies1 = lazyResult,
                randomCountryAndGenre1 = "${randomCountry?.country} - ${randomGenre?.genre}"
            )
        }
    }

    fun getDynamicCollection2() {
        if (state.value.dynamicMovies1 == null) {
            val randomCountry = state.value.countriesAndGenres?.countries?.random()
            val randomGenre = state.value.countriesAndGenres?.genres?.random()
            val lazyResult =
                randomCountry?.let { country ->
                    randomGenre?.let { genre ->
                        collectionsUseCases.getDynamicMovies(
                            listOf(country.id),
                            listOf(genre.id)
                        )
                    }
                }
            _state.value = _state.value.copy(
                dynamicMovies2 = lazyResult,
                randomCountryAndGenre2 = "${randomCountry?.country} - ${randomGenre?.genre}"
            )
        }
    }
}