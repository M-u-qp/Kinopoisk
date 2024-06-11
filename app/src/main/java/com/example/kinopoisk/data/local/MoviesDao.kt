package com.example.kinopoisk.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kinopoisk.data.local.entity.CollectionEntity
import com.example.kinopoisk.data.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(movie: MovieEntity)

    @Delete
    suspend fun delete(movie: MovieEntity)

    @Query("DELETE FROM MovieEntity WHERE id=:id")
    suspend fun deleteMovieById(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCollection(collectionEntity: CollectionEntity)

    @Delete
    suspend fun deleteCollection(collectionEntity: CollectionEntity)

    @Query("SELECT * FROM CollectionEntity")
    fun getCollectionsInDB(): Flow<List<CollectionEntity>>

    @Query("SELECT * FROM MovieEntity WHERE collectionName=:collectionName")
    fun getCollectionInDB(collectionName: String): Flow<List<MovieEntity?>>
}