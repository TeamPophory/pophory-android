package com.teampophory.pophory.network.okhttp

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val headerRequest = originalRequest.newBuilder()
            .header("memberId","1")
            .build()
        return chain.proceed(headerRequest)
    }
}