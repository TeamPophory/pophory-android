package com.teampophory.pophory.data.network.interceptor

import com.teampophory.pophory.data.local.PophoryDataStore
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStore: PophoryDataStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val headerRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer ${dataStore.accessToken}")
            .build()
        return chain.proceed(headerRequest)
    }
}
