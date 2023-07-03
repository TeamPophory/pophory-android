package com.teampophory.pophory.feature.home.mypage.model


data class MyPageInfo(
    val realName: String,
    val photoCount: Int,
    val photos: List<Photo>? = null
) {
    data class Photo(
        val photoId: Long? = null,
        val photoUrl: String? = null
    )
}