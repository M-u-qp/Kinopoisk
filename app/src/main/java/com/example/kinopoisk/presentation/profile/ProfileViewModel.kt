package com.example.kinopoisk.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kinopoisk.domain.usecases.collections.CollectionsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val collectionsUseCases: CollectionsUseCases
): ViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

     suspend fun getBookmarkCollection(nameCollection: String) {
       val bookmarkCollection = collectionsUseCases.getCollectionInDB(nameCollection)
       _state.value = _state.value.copy(bookmarkCollection = bookmarkCollection)
    }
}