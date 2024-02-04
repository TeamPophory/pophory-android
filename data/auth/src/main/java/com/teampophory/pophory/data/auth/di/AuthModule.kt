package com.teampophory.pophory.data.auth.di

import com.teampophory.pophory.auth.repository.AuthRepository
import com.teampophory.pophory.common.qualifier.Secured
import com.teampophory.pophory.data.auth.repository.DefaultAuthRepository
import com.teampophory.pophory.data.auth.service.AuthService
import com.teampophory.pophory.data.auth.service.SignUpService
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
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthService(@Secured retrofit: Retrofit): AuthService = retrofit.create()

    @Provides
    @Singleton
    fun provideSignUpService(@Secured retrofit: Retrofit): SignUpService =
        retrofit.create(SignUpService::class.java)

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @Binds
        @Singleton
        fun provideAuthService(repository: DefaultAuthRepository): AuthRepository
    }
}
