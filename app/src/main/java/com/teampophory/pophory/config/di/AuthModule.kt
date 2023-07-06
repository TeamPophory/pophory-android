package com.teampophory.pophory.config.di

import com.teampophory.pophory.data.network.service.AlbumService
import com.teampophory.pophory.data.network.service.AuthService
import com.teampophory.pophory.data.repository.auth.AuthRepository
import com.teampophory.pophory.data.repository.auth.DefaultAuthRepository
import com.teampophory.pophory.network.retrofit.signup.RetrofitSignUpNetwork
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideSignUpService(retrofit: Retrofit): RetrofitSignUpNetwork =
        retrofit.create(RetrofitSignUpNetwork::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @Binds
        @Singleton
        fun provideAuthService(repository: DefaultAuthRepository): AuthRepository
    }
}
