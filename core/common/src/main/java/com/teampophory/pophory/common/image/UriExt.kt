package com.teampophory.pophory.common.image

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Size
import androidx.core.net.toFile
import java.io.File

fun Uri.getImageSize(context: Context): Size {
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }
    BitmapFactory.decodeFile(toImageContent(context)?.path, options)
    return Size(options.outWidth, options.outHeight)
}

fun Uri?.toImageContent(context: Context): Uri? {
    return if (this?.scheme?.equals(ContentResolver.SCHEME_CONTENT) == true) {
        // 기본 갤러리 앱 등에서 받은 content:/// Uri를 file:/// 스키마의 Uri로 변경
        try {
            toFileUri(context)
        } catch (e: Exception) {
            this
        }
    } else {
        this
    }
}

@SuppressLint("Range")
private fun Uri?.toFileUri(context: Context): Uri? {
    return context.contentResolver
        .query(this!!, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                return@use Uri.fromFile(File(cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA))))
            }
            return@use null
        }
}
