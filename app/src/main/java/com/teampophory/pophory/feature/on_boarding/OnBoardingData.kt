package com.teampophory.pophory.feature.on_boarding

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toDrawable

data class OnBoardingData(
    val image : String
) {
    val background: Drawable
        get() = Color.parseColor(image).toDrawable()
}