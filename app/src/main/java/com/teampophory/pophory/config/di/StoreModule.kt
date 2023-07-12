package com.teampophory.pophory.config.di

import com.teampophory.pophory.config.di.qualifier.Secured
import com.teampophory.pophory.data.repository.store.DefaultStoreRepository
import com.teampophory.pophory.data.repository.store.StoreRepository
import com.teampophory.pophory.network.StoreNetworkDataSource
import com.teampophory.pophory.network.retrofit.store.RetrofitStoreNetwork
import com.teampophory.pophory.network.retrofit.store.RetrofitStoreNetworkApi
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
object StoreModule {
    @Provides
    @Singleton
    fun provideStoreNetworkService(@Secured retrofit: Retrofit): RetrofitStoreNetworkApi = retrofit.create()

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @Binds
        @Singleton
        fun bindStoreRepository(defaultStoreRepository: DefaultStoreRepository): StoreRepository

        @Binds
        @Singleton
        fun bindStoreNetworkDataSource(retrofitStoreNetwork: RetrofitStoreNetwork): StoreNetworkDataSource
    }
}