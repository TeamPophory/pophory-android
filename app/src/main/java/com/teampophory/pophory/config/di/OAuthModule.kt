package com.teampophory.pophory.config.di

import com.kakao.sdk.user.UserApiClient
import com.teampophory.pophory.config.di.qualifier.Kakao
import com.teampophory.pophory.feature.auth.social.KakaoAuthService
import com.teampophory.pophory.feature.auth.social.OAuthService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ActivityComponent::class)
object OAuthModule {
    @Provides
    @ActivityScoped
    fun provideKakaoApiClient(): UserApiClient = UserApiClient.instance

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @ActivityScoped
        @Binds
        @Kakao
        fun bindKakaoAuthService(service: KakaoAuthService): OAuthService
    }
}
