package com.teampophory.pophory.config.di

import com.kakao.sdk.user.UserApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object OAuthModule {
    @Provides
    @ActivityScoped
    fun provideKakaoApiClient(): UserApiClient = UserApiClient.instance
}
