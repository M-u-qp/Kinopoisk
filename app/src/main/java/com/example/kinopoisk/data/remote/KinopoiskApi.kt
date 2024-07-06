package com.example.kinopoisk.data.remote

import com.example.kinopoisk.data.remote.dto.CollectionsResponse
import com.example.kinopoisk.data.remote.dto.CountriesAndGenresResponse
import com.example.kinopoisk.data.remote.dto.MovieResponse
import com.example.kinopoisk.data.remote.dto.SearchMoviesResponse
import com.example.kinopoisk.data.remote.dto.ListStaffResponse
import com.example.kinopoisk.data.remote.dto.MovieGalleryResponse
import com.example.kinopoisk.data.remote.dto.SearchFilterMoviesResponse
import com.example.kinopoisk.data.remote.dto.SerialSeasonsResponse
import com.example.kinopoisk.data.remote.dto.SimilarMoviesResponse
import com.example.kinopoisk.data.remote.dto.StaffResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskApi {

    //Коллекции
    @GET("/api/v2.2/films/collections")
    suspend fun getTopPopularAll(
        @Header("X-API-KEY") apiKey: String,
        @Query("type") type: String,
        @Query("page") page: Int
    ): CollectionsResponse

    //Поиск
    @GET("/api/v2.1/films/search-by-keyword")
    suspend fun searchMovies(
        @Header("X-API-KEY") apiKey: String,
        @Query("keyword") keyword: String,
        @Query("page") page: Int
    ): SearchMoviesResponse

    //Список стран и жанров для фильтра
    @GET("/api/v2.2/films/filters")
    suspend fun getCountriesAndGenres(
        @Header("X-API-KEY") apiKey: String
    ): Response<CountriesAndGenresResponse>

    //Получить сезоны сериала
    @GET("/api/v2.2/films/{id}/seasons")
    suspend fun getSerialSeasons(
        @Header("X-API-KEY") apiKey: String,
        @Path("id") id: Int
    ): Response<SerialSeasonsResponse>

    //Получить список фильмов по различным фильтрам
    @GET("/api/v2.2/films")
    suspend fun searchFilterMovies(
        @Header("X-API-KEY") apiKey: String,
        @Query("countries") countries: List<Int>,
        @Query("genres") genres: List<Int>,
        @Query("order") order: String,
        @Query("type") type: String,
        @Query("ratingFrom") ratingFrom: Int,
        @Query("ratingTo") ratingTo: Int,
        @Query("yearFrom") yearFrom: Int,
        @Query("yearTo") yearTo: Int,
        @Query("page") page: Int
    ): SearchFilterMoviesResponse

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

    //Галерея фильма
    @GET("/api/v2.2/films/{id}/images")
    suspend fun getGalleryMovie(
        @Header("X-API-KEY") apiKey: String,
        @Path("id") id: Int,
        @Query("type") type: String,
        @Query("page") page: Int
    ): MovieGalleryResponse

    //Похожие фильмы
    @GET("/api/v2.2/films/{id}/similars")
    suspend fun getSimilarMovies(
        @Header("X-API-KEY") apiKey: String,
        @Path("id") id: Int
    ): Response<SimilarMoviesResponse>
}