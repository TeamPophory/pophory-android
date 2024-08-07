package com.teampophory.pophory.network.interceptor

import com.teampophory.pophory.network.datastore.PophoryDataStore
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStore: PophoryDataStore,
) : Interceptor {
    private val encodedToken: String
        get() = "Bearer ${dataStore.accessToken}"

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        if (!shouldRequestAuthenticatedHeaders(originalRequest.url.encodedPath)) {
            return chain.proceed(originalRequest)
        }
        val headerRequest = originalRequest.newBuilder()
            .header("Authorization", encodedToken)
            .build()
        return chain.proceed(headerRequest)
    }

    private fun shouldRequestAuthenticatedHeaders(encodedPath: String) = when (encodedPath) {
        "/api/v2/auth" -> false
        else -> true
    }
}
