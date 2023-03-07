package com.techlads.muzika.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Blue,
    primaryVariant = Blue,
    secondary = White
)

private val LightColorPalette = lightColors(
    primary = White,
    primaryVariant = White,
    secondary = Blue
)

@Composable
/*fun MuzikaTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {*/
fun MuzikaTheme(darkTheme: Boolean = true, content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}