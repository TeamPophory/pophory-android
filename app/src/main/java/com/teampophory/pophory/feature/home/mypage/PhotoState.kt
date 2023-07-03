package com.teampophory.pophory.feature.home.mypage

import com.teampophory.pophory.feature.album.model.PhotoList

sealed class PhotoState {
    object Uninitialized : PhotoState()
    object Loading : PhotoState()
    data class SuccessPhotos(val data: PhotoList) : PhotoState()
    data class Error(val error: Throwable) : PhotoState()
}