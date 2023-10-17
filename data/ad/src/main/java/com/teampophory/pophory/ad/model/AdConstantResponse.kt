package com.teampophory.pophory.ad.model


import com.teampophory.pophory.ad.entity.AdIdentifier
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
@Serializable
data class AdConstantResponse(
    @SerialName("adId")
    val adId: String? = null,
    @SerialName("adName")
    val adName: String? = null
)


fun AdConstantResponse.toAdConstant() = AdIdentifier(
    id = adId ?: "",
    name = adName ?: ""
)
