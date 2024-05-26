package com.example.kinopoisk.domain.usecases

import com.example.kinopoisk.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val localUserManager: LocalUserManager
) {

     operator fun invoke(): Flow<Boolean> {
        return localUserManager.readAppEntry()
    }
}