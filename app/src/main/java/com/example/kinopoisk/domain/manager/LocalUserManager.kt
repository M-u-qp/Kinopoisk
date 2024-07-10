package com.example.kinopoisk.domain.manager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {

    suspend fun saveAppEntry()
    fun readAppEntry(): Flow<Boolean>

    suspend fun saveFlagCollections()
    fun readFlagCollections(): Flow<Boolean>
}