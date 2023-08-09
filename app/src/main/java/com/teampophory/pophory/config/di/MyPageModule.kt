package com.teampophory.pophory.config.di

import com.teampophory.pophory.common.qualifier.Secured
import com.teampophory.pophory.data.network.service.MypageService
import com.teampophory.pophory.data.repository.my.DefaultMyPageRepository
import com.teampophory.pophory.data.repository.my.MyPageRepository
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
object MyPageModule {
    @Provides
    @Singleton
    fun provideMyPageNetworkService(@Secured retrofit: Retrofit): MypageService = retrofit.create()

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @Binds
        @Singleton
        fun bindMyPageRepository(defaultMyPageRepository: DefaultMyPageRepository): MyPageRepository
    }
}