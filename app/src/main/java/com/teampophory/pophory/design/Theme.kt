package com.teampophory.pophory.design

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Stable
class PophoryColors(
    white: Color,
    primary: Color,
    red: Color,
    onSurface40: Color,
    onSurface30: Color,
    onSurface20: Color,
    onSurface10: Color,
    isLight: Boolean
) {
    var white by mutableStateOf(white)
        private set
    var primary by mutableStateOf(primary)
        private set
    var red by mutableStateOf(red)
        private set
    var onSurface40 by mutableStateOf(onSurface40)
        private set
    var onSurface30 by mutableStateOf(onSurface30)
        private set
    var onSurface20 by mutableStateOf(onSurface20)
        private set
    var onSurface10 by mutableStateOf(onSurface10)
        private set

    var isLight by mutableStateOf(isLight)

    fun copy(): PophoryColors = PophoryColors(
        white = white,
        primary = primary,
        red = red,
        onSurface40,
        onSurface30,
        onSurface20,
        onSurface10,
        isLight
    )

    fun update(other: PophoryColors) {
        white = other.white
        primary = other.primary
        red = other.red
        onSurface40 = other.onSurface40
        onSurface30 = other.onSurface30
        onSurface20 = other.onSurface20
        onSurface10 = other.onSurface10
        isLight = other.isLight
    }
}

fun pophoryLightColors(
    white: Color = White,
    primary: Color = Purple,
    red: Color = Error,
    onSurface40: Color = Gray400,
    onSurface30: Color = Gray300,
    onSurface20: Color = Gray200,
    onSurface10: Color = Gray100,
    isLight: Boolean = true
): PophoryColors {
    return PophoryColors(
        white = white,
        primary = primary,
        red = red,
        onSurface40 = onSurface40,
        onSurface30 = onSurface30,
        onSurface20 = onSurface20,
        onSurface10 = onSurface10,
        isLight = isLight
    )
}

private val LocalPophoryColors = staticCompositionLocalOf<PophoryColors> {
    error("No SoptColors provided")
}

object PophoryTheme {
    val colors: PophoryColors @Composable get() = LocalPophoryColors.current
}

@Composable
fun ProvidePophoryColor(
    colors: PophoryColors,
    content: @Composable () -> Unit
) {
    val provideColors = remember { colors.copy() }
    provideColors.update(colors)
    CompositionLocalProvider(
        LocalPophoryColors provides provideColors,
        content = content
    )
}

@Composable
fun PophoryTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = pophoryLightColors()
    ProvidePophoryColor(colors) {
        MaterialTheme(content = content)
    }
}
