package com.teampophory.pophory.feature.album.list

import com.teampophory.pophory.feature.album.model.PhotoItem

sealed class AlbumListState {
    object Uninitialized : AlbumListState()
    object Loading : AlbumListState()
    data class SuccessAlbums(val data: List<PhotoItem>) : AlbumListState()
    data class Error(val error: Throwable) : AlbumListState()
}