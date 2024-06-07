package com.example.kinopoisk.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.kinopoisk.domain.model.DailyQuota
import com.example.kinopoisk.domain.usecases.collections.CollectionsUseCases
import com.example.kinopoisk.domain.usecases.common.CommonUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val collectionsUseCases: CollectionsUseCases,
    private val commonUseCases: CommonUseCases
) : ViewModel() {

    private val _apiState = mutableStateOf<DailyQuota?>(null)
    val apiState: State<DailyQuota?> = _apiState

    val topPopularAll = collectionsUseCases.getCollections().cachedIn(viewModelScope)
    init {
        getApiCount()
    }
    private fun getApiCount() {
        viewModelScope.launch {
            try {
                val countApi = commonUseCases.apiCount()
                _apiState.value = countApi
                Log.d("TAG", apiState.value.toString())
            } catch (e:Exception) {
                e.printStackTrace()
            }

        }
    }
}