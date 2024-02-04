package com.teampophory.pophory.feature.album.detail

sealed class AlbumDetailState {
    data object Uninitialized : AlbumDetailState()
    data object SuccessDeleteAlbum : AlbumDetailState()

    data class Error(
        val message: String,
    ) : AlbumDetailState()
}
