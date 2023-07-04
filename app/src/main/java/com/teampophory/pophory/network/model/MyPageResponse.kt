package com.teampophory.pophory.network.model

import com.teampophory.pophory.feature.home.mypage.MyPageDisplayItem
import com.teampophory.pophory.feature.home.mypage.MyPageInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MyPageResponse(
    @SerialName("realName")
    val realName: String,
    @SerialName("nickName")
    val nickname: String,
    @SerialName("profileImage")
    val profileImage: String,
    @SerialName("photoCount")
    val photoCount: Int,
    @SerialName("photos")
    val photos: List<Photo>
) {
    @Serializable
    data class Photo(
        @SerialName("photoId")
        val photoId: Long,
        @SerialName("photoUrl")
        val photoUrl: String,
        @SerialName("studio")
        val studio: String,
        @SerialName("takenAt")
        val takenAt: String,
        @SerialName("width")
        val width: Long,
        @SerialName("height")
        val height: String
    )

    fun toItems(): List<MyPageDisplayItem> {
        val list: ArrayList<MyPageDisplayItem> =
            arrayListOf(MyPageDisplayItem.Profile(realName, nickname, photoCount))
        list.addAll(
            photos?.map {
                MyPageDisplayItem.Photo(MyPageInfo.Photo(it.photoId ?: 0, it.photoUrl.orEmpty()))
            } ?: emptyList()
        )
        return list
    }
}
