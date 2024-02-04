package com.teampophory.pophory.common.image

import android.graphics.Bitmap
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okio.BufferedSink

class BitmapRequestBody(
    private val bitmap: Bitmap,
    private val bytes: Long,
    private val compressRate: Int = 100,
) : RequestBody() {
    override fun contentLength() = bytes
    override fun contentType(): MediaType = "image/jpeg".toMediaType()

    override fun writeTo(sink: BufferedSink) {
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressRate, sink.outputStream())
    }
}
