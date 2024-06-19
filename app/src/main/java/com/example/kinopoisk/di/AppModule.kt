package com.example.kinopoisk.di

import android.app.Application
import androidx.room.Room
import com.example.kinopoisk.data.local.MoviesDao
import com.example.kinopoisk.data.local.MoviesDatabase
import com.example.kinopoisk.data.local.MoviesTypeConvertor
import com.example.kinopoisk.data.manager.LocalUserManagerImpl
import com.example.kinopoisk.data.remote.KinopoiskApi
import com.example.kinopoisk.data.repository.KinopoiskRepositoryImpl
import com.example.kinopoisk.domain.manager.LocalUserManager
import com.example.kinopoisk.domain.repository.KinopoiskRepository
import com.example.kinopoisk.domain.usecases.app_entry.AppEntryUseCases
import com.example.kinopoisk.domain.usecases.app_entry.ReadAppEntry
import com.example.kinopoisk.domain.usecases.app_entry.SaveAppEntry
import com.example.kinopoisk.domain.usecases.collections.AddCollection
import com.example.kinopoisk.domain.usecases.collections.CollectionsUseCases
import com.example.kinopoisk.domain.usecases.collections.DeleteCollection
import com.example.kinopoisk.domain.usecases.collections.GetCollectionInDB
import com.example.kinopoisk.domain.usecases.collections.GetCollections
import com.example.kinopoisk.domain.usecases.collections.GetCollectionsInDB
import com.example.kinopoisk.domain.usecases.movies.DeleteCollectionMovies
import com.example.kinopoisk.domain.usecases.movies.DeleteMovie
import com.example.kinopoisk.domain.usecases.movies.DeleteMovieById
import com.example.kinopoisk.domain.usecases.movies.GetAllMoviesInDB
import com.example.kinopoisk.domain.usecases.movies.GetMovie
import com.example.kinopoisk.domain.usecases.movies.MoviesUseCases
import com.example.kinopoisk.domain.usecases.movies.SearchMovies
import com.example.kinopoisk.domain.usecases.movies.UpsertMovie
import com.example.kinopoisk.domain.usecases.staff.GetListStaff
import com.example.kinopoisk.domain.usecases.staff.GetStaff
import com.example.kinopoisk.domain.usecases.staff.StaffUseCases
import com.example.kinopoisk.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )

    @Provides
    @Singleton
    fun provideKinopoiskApi(): KinopoiskApi {
        return Retrofit.Builder()
            .client(
                OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().also {
                    it.level = HttpLoggingInterceptor.Level.BODY
                }).build()
            )
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KinopoiskApi::class.java)
    }

    @Provides
    @Singleton
    fun provideKinopoiskRepository(
        kinopoiskApi: KinopoiskApi,
        application: Application,
        moviesDao: MoviesDao
    ): KinopoiskRepository = KinopoiskRepositoryImpl(
        kinopoiskApi = kinopoiskApi,
        context = application,
        moviesDao = moviesDao
    )

    @Provides
    @Singleton
    fun provideCollectionsUseCases(
        kinopoiskRepository: KinopoiskRepository
    ): CollectionsUseCases {
        return CollectionsUseCases(
            getCollections = GetCollections(kinopoiskRepository),
            getCollectionInDB = GetCollectionInDB(kinopoiskRepository),
            getCollectionsInDB = GetCollectionsInDB(kinopoiskRepository),
            addCollection = AddCollection(kinopoiskRepository),
            deleteCollection = DeleteCollection(kinopoiskRepository)
        )
    }

    @Provides
    @Singleton
    fun provideMoviesUseCases(
        kinopoiskRepository: KinopoiskRepository,
    ): MoviesUseCases {
        return MoviesUseCases(
            searchMovies = SearchMovies(kinopoiskRepository),
            getMovie = GetMovie(kinopoiskRepository),
            upsertMovie = UpsertMovie(kinopoiskRepository),
            deleteMovie = DeleteMovie(kinopoiskRepository),
            deleteMovieById = DeleteMovieById(kinopoiskRepository),
            getAllMoviesInDB = GetAllMoviesInDB(kinopoiskRepository),
            deleteCollectionMovies = DeleteCollectionMovies(kinopoiskRepository)
        )
    }

    @Provides
    @Singleton
    fun provideStaffUseCases(
        kinopoiskRepository: KinopoiskRepository
    ): StaffUseCases {
        return StaffUseCases(
            getListStaff = GetListStaff(kinopoiskRepository),
            getStaff = GetStaff(kinopoiskRepository)
        )
    }

    @Provides
    @Singleton
    fun provideMoviesDatabase(
        application: Application
    ): MoviesDatabase {
//        return Room.databaseBuilder(
        return Room.inMemoryDatabaseBuilder(
            context = application,
            klass = MoviesDatabase::class.java
//            name = MOVIES_DATABASE_NAME
        )
            .addTypeConverter(MoviesTypeConvertor())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMoviesDao(
        moviesDatabase: MoviesDatabase
    ): MoviesDao = moviesDatabase.moviesDao
}