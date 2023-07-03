package com.teampophory.pophory.network.model

import com.teampophory.pophory.feature.home.mypage.model.MyPageInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MyPageInfoResponse(
    @SerialName("realName")
    val realName: String,
    @SerialName("nickName")
    val nickname: String,
    @SerialName("profileImage")
    val profileImage: String,
    @SerialName("photoCount")
    val photoCount: Int,
    @SerialName("photos")
    val photos: List<Photo>? = null
) {
    @Serializable
    data class Photo(
        @SerialName("photoId")
        val photoId: Long? = null,
        @SerialName("photoUrl")
        val photoUrl: String? = null,
        @SerialName("studio")
        val studio: String? = null,
        @SerialName("takenAt")
        val takenAt: String? = null
    )
}

fun MyPageInfoResponse.toMyPageInfo(): MyPageInfo {
    return MyPageInfo(
        realName = this.realName,
        nickname = this.nickname,
        photoCount = this.photoCount,
        photos = this.photos?.map { photo ->
            MyPageInfo.Photo(
                photoId = photo.photoId ?: 0,
                photoUrl = photo.photoUrl ?: ""
            )
        } ?: emptyList()
    )
}