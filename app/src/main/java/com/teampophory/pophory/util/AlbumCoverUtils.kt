package com.teampophory.pophory.util

import com.teampophory.pophory.R

fun Int.toCoverDrawable() = when (this) {
    1 -> R.drawable.ic_album_cover_friends
    2 -> R.drawable.ic_album_cover_love
    3 -> R.drawable.ic_album_cover_myalbum
    4 -> R.drawable.ic_album_cover_collectbook
    else -> throw IllegalArgumentException("Invalid album cover id $this")
}