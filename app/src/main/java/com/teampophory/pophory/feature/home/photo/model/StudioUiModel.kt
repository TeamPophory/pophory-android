package com.teampophory.pophory.feature.home.photo.model

import com.teampophory.pophory.data.model.photo.Studio

data class StudioUiModel(
    val id: Long,
    val name: String,
    val isSelected: Boolean
)

fun Studio.toUiModel(isSelected: Boolean) = StudioUiModel(id, name, isSelected)
