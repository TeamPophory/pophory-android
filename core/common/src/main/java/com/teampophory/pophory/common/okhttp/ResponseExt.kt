package com.teampophory.pophory.common.okhttp

import retrofit2.HttpException
import retrofit2.Response


fun <T> Response<T>.getResponseBodyOrThrow(): T {
    if (this.isSuccessful) {
        return this.body() ?: Unit as T
    } else {
        throw HttpException(this)
    }
}