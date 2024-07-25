package com.teampophory.pophory.config.remote.di

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import com.teampophory.pophory.config.remote.datasource.RemoteConfigDataSource
import com.teampophory.pophory.config.remote.repository.RemoteConfigRepository
import com.teampophory.pophory.data.config.repository.DefaultRemoteConfigRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteConfigModule {
    @Provides
    @Singleton
    fun provideFirebaseRemoteConfig(): FirebaseRemoteConfig {
        return FirebaseRemoteConfig.getInstance().apply {
            setConfigSettingsAsync(remoteConfigSettings {
                minimumFetchIntervalInSeconds = 3600
            })
            setDefaultsAsync(mapOf("minimum_update_version_android" to "1.4.0"))
        }
    }

    @Provides
    @Singleton
    fun provideRemoteConfigDataSource(
        firebaseRemoteConfig: FirebaseRemoteConfig
    ): RemoteConfigDataSource {
        return RemoteConfigDataSource(firebaseRemoteConfig)
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface RemoteConfigBinderModule {
    @Binds
    @Singleton
    fun bindConfigRepository(
        defaultConfigRepository: DefaultRemoteConfigRepository
    ): RemoteConfigRepository
}
