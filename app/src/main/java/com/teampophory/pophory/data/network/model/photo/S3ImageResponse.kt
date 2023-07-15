package com.teampophory.pophory.data.network.model.photo


import com.teampophory.pophory.domain.model.S3Image
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class S3ImageResponse(
    @SerialName("fileName")
    val fileName: String? = null,
    @SerialName("presignedUrl")
    val preSignedUrl: String? = null
) {
    fun toS3Image() = S3Image(
        fileName = fileName.orEmpty(),
        preSignedUrl = preSignedUrl.orEmpty()
    )
}