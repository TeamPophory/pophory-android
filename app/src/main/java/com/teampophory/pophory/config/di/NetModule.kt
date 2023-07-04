package com.teampophory.pophory.config.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.teampophory.pophory.BuildConfig
import com.teampophory.pophory.config.di.qualifier.Auth
import com.teampophory.pophory.config.di.qualifier.Log
import com.teampophory.pophory.data.network.interceptor.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetModule {
    private const val PophoryBaseUrl = BuildConfig.POPHORY_BASE_URL

    @Singleton
    @Provides
    fun provideJsonConverterFactory(): Converter.Factory {
        return Json.asConverterFactory("application/json".toMediaType())
    }

    @Singleton
    @Provides
    @Log
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        )

    @Singleton
    @Provides
    @Auth
    fun provideAuthInterceptor(interceptor: AuthInterceptor): Interceptor = interceptor

    @Singleton
    @Provides
    fun provideOkHttpClient(
        @Log logInterceptor: Interceptor,
        @Auth authInterceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logInterceptor)
        .addInterceptor(authInterceptor)
        .build()

    // TODO by Nunu BaseUrl 변경
    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(PophoryBaseUrl)
        .client(client)
        .addConverterFactory(converterFactory)
        .build()
}
