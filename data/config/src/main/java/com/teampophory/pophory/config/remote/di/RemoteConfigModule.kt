package com.teampophory.pophory.config.remote.di

import com.teampophory.pophory.common.qualifier.Secured
import com.teampophory.pophory.config.remote.model.MinimumVersionService
import com.teampophory.pophory.config.remote.repository.DefaultRemoteConfigRepository
import com.teampophory.pophory.config.remote.repository.RemoteConfigRepository
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
object RemoteConfigModule {

    @Provides
    @Singleton
    fun provideMinimumVersionService(@Secured retrofit: Retrofit): MinimumVersionService =
        retrofit.create()

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @Binds
        @Singleton
        fun provideRemoteConfigRepository(repository: DefaultRemoteConfigRepository): RemoteConfigRepository
    }
}