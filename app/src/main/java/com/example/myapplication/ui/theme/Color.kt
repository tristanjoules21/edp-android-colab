package com.example.myapplication.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val OrangePrimary = Color(0xFFFF9800)
val OrangeSecondary = Color(0xFFFFB74D)
val OrangeTertiary = Color(0xFFFFE0B2)

val DarkOrangePrimary = Color(0xFFF57C00)
val DarkOrangeSecondary = Color(0xFFEF6C00)
val DarkOrangeTertiary = Color(0xFFE65100)

val Background = Color(0xFFFFF3E0)
val Surface = Color(0xFFFFFFFF)
val OnPrimary = Color(0xFFFFFFFF)
val OnSecondary = Color(0xFF000000)
val OnBackground = Color(0xFF212121)
val OnSurface = Color(0xFF212121)

@Composable
fun MySocialTheme(darkTheme: Boolean = false, content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = DarkOrangePrimary,
            secondary = DarkOrangeSecondary,
            tertiary = DarkOrangeTertiary,
            background = Color(0xFF121212),
            surface = Color(0xFF1E1E1E),
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color.White,
            onSurface = Color.White
        )
    } else {
        lightColorScheme(
            primary = OrangePrimary,
            secondary = OrangeSecondary,
            tertiary = OrangeTertiary,
            background = Background,
            surface = Surface,
            onPrimary = OnPrimary,
            onSecondary = OnSecondary,
            onBackground = OnBackground,
            onSurface = OnSurface
        )
    }

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
