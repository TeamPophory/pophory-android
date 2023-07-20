package com.teampophory.pophory.feature.album.cover

sealed class AlbumEditState {
    object Uninitialized : AlbumEditState()
    object Loading : AlbumEditState()
    object Success : AlbumEditState()
    data class Error(val error: Throwable) : AlbumEditState()
}
