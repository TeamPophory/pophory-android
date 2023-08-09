package com.teampophory.pophory.config.di

import com.kakao.sdk.user.UserApiClient
import com.teampophory.pophory.common.qualifier.Kakao
import com.teampophory.pophory.feature.auth.social.KakaoAuthService
import com.teampophory.pophory.feature.auth.social.OAuthService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object OAuthModule {
    @Provides
    fun provideKakaoApiClient(): UserApiClient = UserApiClient.instance

    @Module
    @InstallIn(ActivityComponent::class)
    interface Binder {
        @Binds
        @Kakao
        fun bindKakaoAuthService(service: KakaoAuthService): OAuthService
    }
}
