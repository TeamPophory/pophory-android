package com.teampophory.pophory.network.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.teampophory.pophory.network.datastore.DefaultPophoryDataStore
import com.teampophory.pophory.network.datastore.PophoryDataStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.security.GeneralSecurityException
import java.security.KeyStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private const val DEBUG_APP_PREFERNCES_NAME = "pophory_debug"
    private const val APP_PREFERENCES_NAME = "pophory"

    @Provides
    @Singleton
    fun provideAppPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences = if (BuildConfig.DEBUG) {
        context.getSharedPreferences(DEBUG_APP_PREFERNCES_NAME, Context.MODE_PRIVATE)
    } else {
        try {
            createEncryptedSharedPreferences(APP_PREFERENCES_NAME, context)
        } catch (e: GeneralSecurityException) {
            deleteMasterKeyEntry()
            deleteExistingPref(APP_PREFERENCES_NAME, context)
            createEncryptedSharedPreferences(APP_PREFERENCES_NAME, context)
        }
    }

    private fun deleteExistingPref(fileName: String, context: Context) {
        context.deleteSharedPreferences(fileName)
    }

    private fun deleteMasterKeyEntry() {
        KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
            deleteEntry(MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        }
    }

    private fun createEncryptedSharedPreferences(
        fileName: String,
        context: Context
    ): SharedPreferences {
        return EncryptedSharedPreferences.create(
            context,
            fileName,
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
