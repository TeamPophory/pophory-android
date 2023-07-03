package com.teampophory.pophory.feature.home.mypage.model


data class MyPageInfo(
    val realName: String,
    val nickname: String,
    val photoCount: Int,
    val photos: List<Photo>
) {
    data class Photo(
        val photoId: Long,
        val photoUrl: String
    )
}