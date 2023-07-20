package com.teampophory.pophory.feature.album.cover.model

import androidx.annotation.DrawableRes

data class AlbumCoverItem(
    val theme: AlbumTheme,
    @DrawableRes val imageRes: Int,
) {
    enum class AlbumTheme {
        FRIEND, LOVE, MY_ALBUM, FAMILY, NONE
    }
}
