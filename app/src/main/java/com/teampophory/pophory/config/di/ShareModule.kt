package com.teampophory.pophory.config.di

import com.teampophory.pophory.config.di.qualifier.Secured
import com.teampophory.pophory.data.repository.share.DefaultShareRepository
import com.teampophory.pophory.data.repository.share.ShareRepository
import com.teampophory.pophory.data.network.service.RetrofitShareNetwork
import com.teampophory.pophory.data.network.service.RetrofitShareNetworkApi
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
object ShareModule {
    @Provides
    @Singleton
    fun provideShareNetworkService(@Secured retrofit: Retrofit): RetrofitShareNetworkApi =
        retrofit.create()

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @Binds
        @Singleton
        fun bindShareRepository(defaultShareRepository: DefaultShareRepository): ShareRepository

    }
}
