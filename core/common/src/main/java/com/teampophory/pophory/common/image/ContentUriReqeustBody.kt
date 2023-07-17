package com.teampophory.pophory.common.image

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.ByteArrayOutputStream

class ContentUriRequestBody(
    context: Context,
    private val uri: Uri?
) : RequestBody() {
    private val contentResolver = context.contentResolver

    private var fileName = ""
    private var size = -1L
    private lateinit var compressedImage: ByteArray

    init {
        if (uri != null) {
            contentResolver.query(
                uri,
                arrayOf(MediaStore.Images.Media.SIZE, MediaStore.Images.Media.DISPLAY_NAME),
                null,
                null,
                null
            )?.use { cursor ->
                if (cursor.moveToFirst()) {
                    size =
                        cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE))
                    fileName =
                        cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                }
            }

            // Compress bitmap
            val originalBitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
            val outputStream = ByteArrayOutputStream()
            val imageSizeMb = size / (1024f * 1024f)
            if (imageSizeMb >= 3) {
                val compressRate = ((3 / imageSizeMb) * 100).toInt()
                originalBitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    compressRate,
                    outputStream
                )
            } else {
                originalBitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    100,
                    outputStream
                )
            }
            compressedImage = outputStream.toByteArray()
            size = compressedImage.size.toLong()
        }
    }

    private fun getFileName() = fileName

    override fun contentLength(): Long = size

    override fun contentType(): MediaType? =
        uri?.let { contentResolver.getType(it)?.toMediaTypeOrNull() }

    override fun writeTo(sink: BufferedSink) {
        sink.write(compressedImage)
    }

    fun toFormData(name: String) = MultipartBody.Part.createFormData(name, getFileName(), this)
}
