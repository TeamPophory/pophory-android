package com.teampophory.pophory.common.image

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Size
import androidx.core.net.toFile

fun Uri.getImageSize(): Size {
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }
    BitmapFactory.decodeFile(toFile().path, options)
    return Size(options.outWidth, options.outHeight)
}
