package com.teampophory.pophory.common.okhttp

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

fun String?.toPlainRequestBody() =
    requireNotNull(this).toRequestBody("text/plain".toMediaTypeOrNull())