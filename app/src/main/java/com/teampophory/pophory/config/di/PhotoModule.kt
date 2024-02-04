package com.teampophory.pophory.config.di

import com.teampophory.pophory.common.qualifier.Secured
import com.teampophory.pophory.common.qualifier.Unsecured
import com.teampophory.pophory.data.network.service.PhotoService
import com.teampophory.pophory.data.repository.photo.DefaultPhotoRepository
import com.teampophory.pophory.domain.repository.photo.PhotoRepository
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
    fun providePhotoNetworkService(@Secured retrofit: Retrofit): PhotoService = retrofit.create()

    @Provides
    @Singleton
    @Unsecured
    fun providePhotoNetworkServiceNonToken(@Unsecured retrofit: Retrofit): PhotoService = retrofit.create()

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @Binds
        @Singleton
        fun bindPhotoRepository(defaultPhotoRepository: DefaultPhotoRepository): PhotoRepository
    }
}
