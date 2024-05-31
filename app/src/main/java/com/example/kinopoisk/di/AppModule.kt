package com.example.kinopoisk.di

import android.app.Application
import com.example.kinopoisk.data.manager.LocalUserManagerImpl
import com.example.kinopoisk.data.remote.KinopoiskApi
import com.example.kinopoisk.data.repository.KinopoiskRepositoryImpl
import com.example.kinopoisk.domain.manager.LocalUserManager
import com.example.kinopoisk.domain.repository.KinopoiskRepository
import com.example.kinopoisk.domain.usecases.app_entry.AppEntryUseCases
import com.example.kinopoisk.domain.usecases.app_entry.ReadAppEntry
import com.example.kinopoisk.domain.usecases.app_entry.SaveAppEntry
import com.example.kinopoisk.domain.usecases.collections.CollectionsUseCases
import com.example.kinopoisk.domain.usecases.collections.GetCollections
import com.example.kinopoisk.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KinopoiskApi::class.java)
    }

    @Provides
    @Singleton
    fun provideKinopoiskRepository(
        kinopoiskApi: KinopoiskApi,
        application: Application
    ): KinopoiskRepository = KinopoiskRepositoryImpl(
        kinopoiskApi = kinopoiskApi,
        context = application
    )

    @Provides
    @Singleton
    fun provideCollectionsUseCases(
        kinopoiskRepository: KinopoiskRepository
    ): CollectionsUseCases {
        return CollectionsUseCases(
            getCollections = GetCollections(kinopoiskRepository)
        )
    }
}