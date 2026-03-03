package org.example.project.presentations.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    background = Background,
    surface = Background,
    onBackground = TextPrimary,
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val extendedColors = ExtendedColors()
    CompositionLocalProvider(
        LocalExtendedColors provides extendedColors
    ) {
        MaterialTheme(
            colorScheme = LightColorScheme,
            typography = AppTypography,
            content = content
        )
    }
}
