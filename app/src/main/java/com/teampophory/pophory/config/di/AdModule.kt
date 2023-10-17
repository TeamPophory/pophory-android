package com.teampophory.pophory.config.di

import com.teampophory.pophory.ad.repository.AdRepository
import com.teampophory.pophory.ad.repository.DefaultAdRepository
import com.teampophory.pophory.ad.service.AdService
import com.teampophory.pophory.common.qualifier.Secured
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
object AdModule {

    @Provides
    @Singleton
    fun provideAdService(@Secured retrofit: Retrofit): AdService = retrofit.create()

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @Binds
        @Singleton
        fun bindAdRepository(defaultAdRepository: DefaultAdRepository): AdRepository
    }
}
