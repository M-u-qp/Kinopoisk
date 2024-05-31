package com.example.kinopoisk.data.remote

import com.example.kinopoisk.data.remote.dto.CollectionsResponse
import com.example.kinopoisk.data.remote.dto.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface KinopoiskApi {

    @GET("/api/v2.2/films/collections?type=TOP_POPULAR_ALL")
    suspend fun getTopPopularAll(
        @Header("X-API-KEY") apiKey: String,
        @Query("page") page: Int
    ): CollectionsResponse

    @GET("/api/v2.1/films/search-by-keyword")
    suspend fun searchMovies(
        @Header("X-API-KEY") apiKey: String,
        @Query("keyword") keyword: String,
        @Query("page") page: Int
    ): MoviesResponse
}