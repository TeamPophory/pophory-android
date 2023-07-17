package com.teampophory.pophory.network.model

import com.teampophory.pophory.feature.home.mypage.model.Profile
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MyPageResponse(
    @SerialName("id")
    val id: Long,
    @SerialName("realName")
    val realName: String,
    @SerialName("nickname")
    val nickname: String,
    @SerialName("profileImageUrl")
    val profileImageUrl: String?,
    @SerialName("photoCount")
    val photoCount: Int
) {
    fun toProfile(): Profile {
        return Profile(id,realName, nickname, profileImageUrl,photoCount)
    }
}