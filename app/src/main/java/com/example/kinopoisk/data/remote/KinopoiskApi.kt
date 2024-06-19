package com.example.kinopoisk.data.remote

import com.example.kinopoisk.data.remote.dto.CollectionsResponse
import com.example.kinopoisk.data.remote.dto.MovieResponse
import com.example.kinopoisk.data.remote.dto.SearchMoviesResponse
import com.example.kinopoisk.data.remote.dto.ListStaffResponse
import com.example.kinopoisk.data.remote.dto.StaffResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApi {

    //Коллекции
    @GET("/api/v2.2/films/collections?type=TOP_POPULAR_ALL")
    suspend fun getTopPopularAll(
        @Header("X-API-KEY") apiKey: String,
        @Query("page") page: Int
    ): CollectionsResponse

    //Поиск
    @GET("/api/v2.1/films/search-by-keyword")
    suspend fun searchMovies(
        @Header("X-API-KEY") apiKey: String,
        @Query("keyword") keyword: String,
        @Query("page") page: Int
    ): SearchMoviesResponse

    //Детальное инфо о фильме
    @GET("/api/v2.2/films/{id}")
    suspend fun getMovie(
        @Header("X-API-KEY") apiKey: String,
        @Path("id") id: Int
    ): Response<MovieResponse>

    //Список актеров и т.п.
    @GET("/api/v1/staff")
    suspend fun getListStaff(
        @Header("X-API-KEY") apiKey: String,
        @Query("filmId") filmId: Int
    ): Response<ListStaffResponse>

    //Детальное инфо об актере
    @GET("/api/v1/staff/{id}")
    suspend fun getStaff(
        @Header("X-API-KEY") apiKey: String,
        @Path("id") id: Int
    ): Response<StaffResponse>
}