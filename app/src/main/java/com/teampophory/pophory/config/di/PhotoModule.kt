package com.teampophory.pophory.config.di

import com.teampophory.pophory.data.repository.photo.PhotoRepository
import com.teampophory.pophory.data.repository.photo.DefaultPhotoRepository
import com.teampophory.pophory.network.PhotoNetworkDataSource
import com.teampophory.pophory.network.retrofit.album.RetrofitPhotoNetwork
import com.teampophory.pophory.network.retrofit.album.RetrofitPhotoNetworkApi
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
    fun providePhotoNetworkService(retrofit: Retrofit): RetrofitPhotoNetworkApi = retrofit.create()

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @Binds
        @Singleton
        fun bindPhotoRepository(defaultPhotoRepository: DefaultPhotoRepository): PhotoRepository

        @Binds
        @Singleton
        fun bindPhotoNwtworkDataSource(retrofitPhotoNetwork: RetrofitPhotoNetwork): PhotoNetworkDataSource
    }
}