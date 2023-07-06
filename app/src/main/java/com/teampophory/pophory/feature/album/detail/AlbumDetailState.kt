package com.teampophory.pophory.feature.album.detail

sealed class AlbumDetailState {
    object Uninitialized : AlbumDetailState()
    object SuccessDeleteAlbum : AlbumDetailState()

    data class Error(
        val message: String
    ) : AlbumDetailState()
}