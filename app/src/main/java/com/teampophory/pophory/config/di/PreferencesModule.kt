package com.teampophory.pophory.config.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.teampophory.pophory.BuildConfig
import com.teampophory.pophory.data.local.DefaultPophoryDataStore
import com.teampophory.pophory.data.local.PophoryDataStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    private const val DEBUG_APP_PREFERNCES_NAME = "pophory_debug"
    private const val APP_PREFERENCES_NAME = "pophory"

    @Provides
    @Singleton
    fun provideAppPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences = if (BuildConfig.DEBUG) {
        context.getSharedPreferences(DEBUG_APP_PREFERNCES_NAME, Context.MODE_PRIVATE)
    } else {
        EncryptedSharedPreferences.create(
            context,
            APP_PREFERENCES_NAME,
            MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM

        )
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binder {
        @Singleton
        @Binds
        fun bindAppPreferences(dataStore: DefaultPophoryDataStore): PophoryDataStore
    }
}