package com.example.kinopoisk.data.remote

import com.example.kinopoisk.data.remote.dto.ApiKeyCountResponse
import com.example.kinopoisk.data.remote.dto.CollectionsResponse
import com.example.kinopoisk.data.remote.dto.MovieResponse
import com.example.kinopoisk.data.remote.dto.SearchMoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApi {

    @GET("/api/v1/api_keys/{apiKey}")
    fun checkApiCount(
        @Path("apiKey") apiKey: String
    ): ApiKeyCountResponse

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
    ): SearchMoviesResponse

    @GET("/api/v2.2/films/{id}")
    suspend fun getMovie(
        @Header("X-API-KEY") apiKey: String,
        @Path("id") id: Int
    ): Response<MovieResponse>
}