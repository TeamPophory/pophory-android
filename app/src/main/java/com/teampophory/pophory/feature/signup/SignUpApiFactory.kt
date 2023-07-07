package com.teampophory.pophory.feature.signup

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.teampophory.pophory.BuildConfig
import com.teampophory.pophory.network.retrofit.signup.RetrofitSignUpNetwork
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

object SignUpApiFactory {

    var token = ""

    private const val POPHORY_BASE_URL = BuildConfig.POPHORY_BASE_URL

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val authenticatedRequest = chain.request().newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
                return@addInterceptor chain.proceed(authenticatedRequest)
            }.addInterceptor(HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }).build()
    }

    val retrofitForSignUp: Retrofit by lazy {
        Retrofit.Builder().baseUrl(POPHORY_BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(client).build()
    }

    inline fun <reified T> createSignUpService(): T = retrofitForSignUp.create<T>(T::class.java)
}

object ServicePool {
    val signUpService = SignUpApiFactory.createSignUpService<RetrofitSignUpNetwork>()
}