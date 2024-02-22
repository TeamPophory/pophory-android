package com.teampophory.pophory.util

import com.teampophory.pophory.designsystem.type.DesignSystemResources

fun Int.toCoverDrawable() = when (this) {
    1 -> DesignSystemResources.ic_album_cover_friends_1
    2 -> DesignSystemResources.ic_album_cover_friends_2
    3 -> DesignSystemResources.ic_album_cover_love_1
    4 -> DesignSystemResources.ic_album_cover_love_2
    5 -> DesignSystemResources.ic_album_cover_me_1
    6 -> DesignSystemResources.ic_album_cover_me_2
    7 -> DesignSystemResources.ic_album_cover_family_1
    8 -> DesignSystemResources.ic_album_cover_family_2
    else -> throw IllegalArgumentException("Invalid album cover id $this")
}
