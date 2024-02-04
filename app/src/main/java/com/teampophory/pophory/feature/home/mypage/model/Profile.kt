package com.teampophory.pophory.feature.home.mypage.model

data class Profile(
    val id: Long,
    val realName: String,
    val nickname: String,
    val profileImageUrl: String?,
    val photoCount: Int,
)
