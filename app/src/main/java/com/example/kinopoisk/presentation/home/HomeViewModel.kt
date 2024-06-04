package com.example.kinopoisk.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kinopoisk.domain.usecases.collections.CollectionsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val collectionsUseCases: CollectionsUseCases,
) : ViewModel() {

    val topPopularAll = collectionsUseCases.getCollections().cachedIn(viewModelScope)
}