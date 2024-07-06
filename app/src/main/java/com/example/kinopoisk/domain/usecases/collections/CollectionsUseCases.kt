package com.example.kinopoisk.domain.usecases.collections

data class CollectionsUseCases(
    val getCollections: GetCollections,
    val getCollectionInDB: GetCollectionInDB,
    val getCollectionsInDB: GetCollectionsInDB,
    val addCollection: AddCollection,
    val deleteCollection: DeleteCollection,
    val getSimilarMovies: GetSimilarMovies,
    val getPremieres: GetPremieres
)
