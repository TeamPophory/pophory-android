package com.teampophory.pophory.network.di

import com.teampophory.pophory.common.qualifier.Unsecured
import com.teampophory.pophory.network.api.RefreshApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RefreshModule {
    @Provides
    @Singleton
    fun provideRefreshApi(@Unsecured retrofit: Retrofit): RefreshApi = retrofit.create()
}
