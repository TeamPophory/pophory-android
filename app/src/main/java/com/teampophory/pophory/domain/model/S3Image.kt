package com.teampophory.pophory.domain.model

data class S3Image(
    val fileName: String,
    val preSignedUrl: String
)