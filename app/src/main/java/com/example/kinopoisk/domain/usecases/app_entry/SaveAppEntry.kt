package com.example.kinopoisk.domain.usecases.app_entry

import com.example.kinopoisk.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserManager: LocalUserManager
) {

    suspend operator fun invoke() {
        localUserManager.saveAppEntry()
    }
}