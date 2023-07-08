package com.teampophory.pophory.feature.home.mypage

import com.teampophory.pophory.feature.album.model.PhotoDetail
import kotlinx.serialization.SerialName

sealed class MyPageDisplayItem {
    data class Profile(
        val realName: String,
        val nickname: String,
        val photoCount: Int
    ) : MyPageDisplayItem()
    data class Photo(val photo: PhotoDetail) : MyPageDisplayItem()
    object Empty : MyPageDisplayItem()
}