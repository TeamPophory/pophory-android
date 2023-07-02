package com.teampophory.pophory.feature.album

import com.teampophory.pophory.feature.album.model.PhotoList

sealed class AlbumState {
    object Uninitialized : AlbumState()
    object Loading : AlbumState()
    data class SuccessAlbums(val data: PhotoList) : AlbumState()
    data class Error(val error: Throwable) : AlbumState()


}