package com.teampophory.pophory.common.view

import android.content.res.Resources

val Int.dp
    get() = this * Resources.getSystem().displayMetrics.density.toInt()

val Int.px
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
