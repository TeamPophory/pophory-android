package com.teampophory.pophory.common.image

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Size

fun Uri.getImageSize(context: Context): Size {
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }

    if (scheme.equals("file")) {
        BitmapFactory.decodeFile(path, options)
    } else {
        context.contentResolver.openInputStream(this)?.use {
            BitmapFactory.decodeStream(it, null, options)
        }
    }
    return Size(options.outWidth, options.outHeight)
}

fun Uri.getAllocatedByte(context: Context): Long {
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }

    if (scheme.equals("file")) {
        BitmapFactory.decodeFile(path, options)
    } else {
        context.contentResolver.openInputStream(this)?.use {
            BitmapFactory.decodeStream(it, null, options)
        }
    }
    return options.outWidth * options.outHeight * 4L
}

fun Uri.getAllocatedBytes(context: Context) = context.contentResolver.query(
    this,
    arrayOf(MediaStore.Images.Media.SIZE, MediaStore.Images.Media.DISPLAY_NAME),
    null,
    null,
    null
)?.use { cursor ->
    if (cursor.moveToFirst()) {
        return@use cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE))
    }
    return@use 0L
}
