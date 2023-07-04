package com.teampophory.pophory.config.di

import com.teampophory.pophory.data.repository.PhotoRepository
import com.teampophory.pophory.data.repository.DefaultPhotoRepository
import com.teampophory.pophory.network.PhotoNetworkDataSource
import com.teampophory.pophory.network.retrofit.album.RetrofitPhotoNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotoModule {
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