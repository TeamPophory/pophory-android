package com.teampophory.pophory.util

import com.teampophory.pophory.R

fun Int.toCoverDrawable() = when (this) {
    1 -> R.drawable.ic_album_cover_friends_1
    2 -> R.drawable.ic_album_cover_friends_2
    3 -> R.drawable.ic_album_cover_love_1
    4 -> R.drawable.ic_album_cover_love_2
    5 -> R.drawable.ic_album_cover_me_1
    6 -> R.drawable.ic_album_cover_me_2
    7 -> R.drawable.ic_album_cover_family_1
    8 -> R.drawable.ic_album_cover_family_2
    else -> throw IllegalArgumentException("Invalid album cover id $this")
}