package com.teampophory.pophory.config.di

import com.teampophory.pophory.data.network.authenticator.PophoryAuthenticator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Authenticator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthBinderModule {
    @Binds
    @Singleton
    fun provideAuthenticator(authenticator: PophoryAuthenticator): Authenticator
}
