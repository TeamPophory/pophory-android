package com.teampophory.pophory.feature.album.list

import com.teampophory.pophory.feature.album.model.PhotoDetail

sealed class AlbumListState {
    object Uninitialized : AlbumListState()
    object Loading : AlbumListState()
    data class SuccessLoadAlbums(val data: List<PhotoDetail>) : AlbumListState()
    data class Error(val error: Throwable) : AlbumListState()
}