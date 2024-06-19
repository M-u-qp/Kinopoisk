package com.example.kinopoisk.presentation.details.details_staff

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kinopoisk.domain.usecases.staff.StaffUseCases
import com.example.kinopoisk.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StaffViewModel @Inject constructor(
    private val staffUseCases: StaffUseCases
) : ViewModel() {

    private val _state = mutableStateOf(StaffState())
    val state: State<StaffState> = _state

    fun getStaffInfo(id: Int) {
        viewModelScope.launch {
            when (val staff = staffUseCases.getStaff(id)) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        staffInfo = staff.data,
                        error = null
                    )
                }

                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        error = staff.exception.toString()
                    )
                }

                else -> Unit
            }
        }
    }
}