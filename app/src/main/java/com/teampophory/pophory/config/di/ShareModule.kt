package com.teampophory.pophory.config.di

import com.teampophory.pophory.common.qualifier.Secured
import com.teampophory.pophory.common.qualifier.Unsecured
import com.teampophory.pophory.data.share.repository.DefaultShareRepository
import com.teampophory.pophory.data.share.service.RetrofitShareNetwork
import com.teampophory.pophory.data.share.service.RetrofitShareNetworkApi
import com.teampophory.pophory.data.share.service.ShareNetworkDataSource
import com.teampophory.pophory.data.share.service.ShareService
import com.teampophory.pophory.share.repository.ShareRepository
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

    @Provides
    @Singleton
    @Secured
    fun provideShareService(@Secured retrofit: Retrofit): ShareService = retrofit.create()

    @Provides
    @Singleton
    @Unsecured
    fun provideUnsecuredShareService(@Secured retrofit: Retrofit): ShareService = retrofit.create()

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @Binds
        @Singleton
        fun bindShareRepository(defaultShareRepository: DefaultShareRepository): ShareRepository

        @Binds
        @Singleton
        fun bindShareNetworkDataSource(retrofitShareNetwork: RetrofitShareNetwork): ShareNetworkDataSource
    }
}

