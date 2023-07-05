package com.teampophory.pophory.network.model

import com.teampophory.pophory.feature.home.mypage.MyPageDisplayItem
import com.teampophory.pophory.feature.home.mypage.MyPageInfo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyPageResponse(
    @SerialName("realName")
    val realName: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("profileImage")
    val profileImage: String?,
    @SerialName("photoCount")
    val photoCount: Int,
    @SerialName("photos")
    val photos: List<Photo>?
) {
    @Serializable
    data class Photo(
        @SerialName("id")
        val id: Int,
        @SerialName("imageUrl")
        val imageUrl: String,
        @SerialName("studio")
        val studio: String,
        @SerialName("takenAt")
        val takenAt: String,
        @SerialName("width")
        val width: Int,
        @SerialName("height")
        val height: Int
    )

    fun toItems(): List<MyPageDisplayItem> {
        val list: ArrayList<MyPageDisplayItem> =
            arrayListOf(MyPageDisplayItem.Profile(realName, nickname, photoCount))
        list.addAll(
            photos?.map {
                MyPageDisplayItem.Photo(MyPageInfo.Photo(it.id ?: 0, it.imageUrl.orEmpty()))
            } ?: emptyList()
        )
        return list
    }
}
