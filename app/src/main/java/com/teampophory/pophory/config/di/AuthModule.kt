package com.teampophory.pophory.config.di

import com.teampophory.pophory.config.di.qualifier.Secured
import com.teampophory.pophory.config.di.qualifier.Unsecured
import com.teampophory.pophory.data.network.authenticator.PophoryAuthenticator
import com.teampophory.pophory.data.network.service.AuthService
import com.teampophory.pophory.data.network.service.RefreshApi
import com.teampophory.pophory.data.repository.auth.AuthRepository
import com.teampophory.pophory.data.repository.auth.DefaultAuthRepository
import com.teampophory.pophory.network.retrofit.signup.RetrofitSignUpNetwork
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthService(@Secured retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun provideSignUpService(@Secured retrofit: Retrofit): RetrofitSignUpNetwork =
        retrofit.create(RetrofitSignUpNetwork::class.java)

    @Provides
    @Singleton
    fun provideRefreshApi(@Unsecured retrofit: Retrofit): RefreshApi = retrofit.create()

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @Binds
        @Singleton
        fun provideAuthService(repository: DefaultAuthRepository): AuthRepository

        @Binds
        @Singleton
        fun provideAuthenticator(authenticator: PophoryAuthenticator): Authenticator
    }
}
