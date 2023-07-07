package com.teampophory.pophory.config.di

import com.teampophory.pophory.data.network.service.AlbumService
import com.teampophory.pophory.data.repository.photo.DefaultPhotoRepository
import com.teampophory.pophory.data.repository.photo.PhotoRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotoModule {
    @Provides
    @Singleton
    fun providePhotoNetworkService(retrofit: Retrofit): AlbumService = retrofit.create()

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @Binds
        @Singleton
        fun bindPhotoRepository(defaultPhotoRepository: DefaultPhotoRepository): PhotoRepository
    }
}