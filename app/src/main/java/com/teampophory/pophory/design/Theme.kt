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
    error: Color,
    onSurface100: Color,
    onSurface50: Color,
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
    var error by mutableStateOf(error)
        private set
    var onSurface100 by mutableStateOf(onSurface100)
        private set
    var onSurface50 by mutableStateOf(onSurface50)
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
        error = error,
        onSurface100 = onSurface100,
        onSurface50 = onSurface50,
        onSurface40 = onSurface40,
        onSurface30 = onSurface30,
        onSurface20 = onSurface20,
        onSurface10 = onSurface10,
        isLight = isLight
    )

    fun update(other: PophoryColors) {
        white = other.white
        primary = other.primary
        error = other.error
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
    error: Color = Error,
    onSurface100: Color = Black,
    onSurface50: Color = Gray500,
    onSurface40: Color = Gray400,
    onSurface30: Color = Gray300,
    onSurface20: Color = Gray200,
    onSurface10: Color = Gray100,
    isLight: Boolean = true
): PophoryColors {
    return PophoryColors(
        white = white,
        primary = primary,
        error = error,
        onSurface100 = onSurface100,
        onSurface50 = onSurface50,
        onSurface40 = onSurface40,
        onSurface30 = onSurface30,
        onSurface20 = onSurface20,
        onSurface10 = onSurface10,
        isLight = isLight
    )
}

private val LocalPophoryColors = staticCompositionLocalOf<PophoryColors> {
    error("No PophoryColors provided")
}
private val LocalPophoryTypography = staticCompositionLocalOf<PophoryTypography> {
    error("No PophoryTypography provided")
}

object PophoryTheme {
    val colors: PophoryColors @Composable get() = LocalPophoryColors.current
    val typography: PophoryTypography @Composable get() = LocalPophoryTypography.current
}

@Composable
fun ProvidePophoryColorAndTypography(
    colors: PophoryColors,
    typography: PophoryTypography,
    content: @Composable () -> Unit
) {
    val provideColors = remember { colors.copy() }
    provideColors.update(colors)
    val provideTypography = remember { typography.copy() }
    provideTypography.update(typography)
    CompositionLocalProvider(
        LocalPophoryColors provides provideColors,
        LocalPophoryTypography provides provideTypography,
        content = content
    )
}

@Composable
fun PophoryTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = pophoryLightColors()
    val typography = PophoryTypography()
    ProvidePophoryColorAndTypography(colors, typography) {
        MaterialTheme(content = content)
    }
}
