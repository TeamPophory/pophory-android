package com.teampophory.pophory.common.compose

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

fun Modifier.bottomBorder(borderWidth: Dp, color: Color): Modifier = drawBehind {
    val strokeWidth = borderWidth.value * density
    val y = size.height - strokeWidth / 2
    drawLine(
        color,
        Offset(0f, y),
        Offset(size.width, y),
        strokeWidth
    )
}
