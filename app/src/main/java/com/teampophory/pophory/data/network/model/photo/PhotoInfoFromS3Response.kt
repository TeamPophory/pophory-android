package com.teampophory.pophory.data.network.model.photo


import com.teampophory.pophory.domain.model.PhotoInfoFromS3
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoInfoFromS3Response(
    @SerialName("fileName")
    val fileName: String? = null,
    @SerialName("presignedUrl")
    val preSignedUrl: String? = null
) {
    fun toPhotoInfoFromS3() = PhotoInfoFromS3(
        fileName = fileName.orEmpty(),
        preSignedUrl = preSignedUrl.orEmpty()
    )
}