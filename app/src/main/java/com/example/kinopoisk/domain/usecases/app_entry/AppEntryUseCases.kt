package com.example.kinopoisk.domain.usecases.app_entry

data class AppEntryUseCases(
    val readAppEntry: ReadAppEntry,
    val saveAppEntry: SaveAppEntry,
    val saveFlagCollections: SaveFlagCollections,
    val readFlagCollections: ReadFlagCollections,
)
