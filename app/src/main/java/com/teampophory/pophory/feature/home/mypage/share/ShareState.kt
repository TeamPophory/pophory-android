package com.teampophory.pophory.feature.home.mypage.share

import com.teampophory.pophory.feature.home.mypage.share.model.PhotoItem

sealed class ShareState {
    object Uninitialized : ShareState()
    object Loading : ShareState()
    data class SuccessPhoto(val data: List<PhotoItem>) : ShareState()
    data class Error(val error: Throwable) : ShareState()
}
