package com.teampophory.pophory.domain.model

data class PhotoInfoFromS3(
    val fileName: String,
    val preSignedUrl: String
)