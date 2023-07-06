package com.teampophory.pophory.config.di

import com.teampophory.pophory.data.repository.my.DefaultMyPageRepository
import com.teampophory.pophory.data.repository.my.MyPageRepository
import com.teampophory.pophory.network.MyPageNetworkDataSource
import com.teampophory.pophory.network.retrofit.mypage.RetrofitMyPageNetwork
import com.teampophory.pophory.network.retrofit.mypage.RetrofitMyPageNetworkApi
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
    fun provideMyPageNetworkService(retrofit: Retrofit): RetrofitMyPageNetworkApi = retrofit.create()

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @Binds
        @Singleton
        fun bindMyPageRepository(defaultMyPageRepository: DefaultMyPageRepository): MyPageRepository

        @Binds
        @Singleton
        fun bindMyPageNetworkDataSource(retrofitMyPageNetwork: RetrofitMyPageNetwork): MyPageNetworkDataSource
    }
}