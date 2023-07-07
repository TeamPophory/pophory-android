package com.teampophory.pophory.feature.home.store.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AlbumItem(
    val id: Int,
    val title: String,
    val albumCover: Int,
    val photoCount: Int
) : Parcelable
