package com.example.kinopoisk.domain.usecases.app_entry

import com.example.kinopoisk.domain.manager.LocalUserManager

class SaveFlagCollections(
    private val localUserManager: LocalUserManager
) {

    suspend operator fun invoke() {
        localUserManager.saveFlagCollections()
    }
}