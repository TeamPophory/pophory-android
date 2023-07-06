package com.teampophory.pophory.feature.home.mypage

sealed class MyPageDisplayItem {
    data class Profile(
        val realName: String,
        val nickname: String,
        val photoCount: Int
    ) : MyPageDisplayItem()
    data class Photo(val photo: MyPageInfo.Photo) : MyPageDisplayItem()
    object Empty : MyPageDisplayItem()
}

data class MyPageInfo(
    val realName: String,
    val nickname: String,
    val photoCount: Int,
    val photos: List<Photo>
) {
    data class Photo(
        val photoId: Int,
        val photoUrl: String
    )
}