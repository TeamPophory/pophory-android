package com.teampophory.pophory.feature.album.list

import com.teampophory.pophory.feature.album.model.PhotoItem

sealed class AlbumListState {
    data object Uninitialized : AlbumListState()
    data object Loading : AlbumListState()
    data class SuccessLoadAlbums(val data: List<PhotoItem>) : AlbumListState()
    data class Error(val error: Throwable) : AlbumListState()
}