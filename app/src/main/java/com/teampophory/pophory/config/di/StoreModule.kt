package com.teampophory.pophory.config.di

import com.teampophory.pophory.common.qualifier.Secured
import com.teampophory.pophory.data.network.service.StoreSerivce
import com.teampophory.pophory.data.repository.store.DefaultStoreRepository
import com.teampophory.pophory.data.repository.store.StoreRepository
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
    fun provideStoreNetworkService(@Secured retrofit: Retrofit): StoreSerivce = retrofit.create()

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @Binds
        @Singleton
        fun bindStoreRepository(defaultStoreRepository: DefaultStoreRepository): StoreRepository
    }
}
