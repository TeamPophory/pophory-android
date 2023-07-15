package com.teampophory.pophory.common.image

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
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