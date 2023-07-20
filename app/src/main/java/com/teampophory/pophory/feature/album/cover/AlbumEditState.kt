package com.teampophory.pophory.feature.album.cover

sealed class AlbumEditState {
    data object Uninitialized : AlbumEditState()
    data object Loading : AlbumEditState()
    data object Success : AlbumEditState()
    data class Error(val error: Throwable) : AlbumEditState()
}
