package org.example.project.presentations.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


data class ExtendedColors (
    val background: Color = Background,
    val mainRed: Color = MainRed,
    val mainBlue: Color = MainBlue,
    val red: Color = Red,
    val redLight: Color = Color(0xFFFFD9D4),
    val textPrimary: Color = TextPrimary,
    val primary: Color = Primary,
    val onPrimary: Color = OnPrimary,
    val white: Color = Color.White,
    val yellow: Color = Color(0xFFFFA400),
    val yellowLight: Color = Color(0xFFFFF0D6),
    val green: Color = Color(0xFF16A634),
    val greenLight: Color = Color(0xFFDAFFE2),
    val gray: Color = Color(0xFF848484),
    val grayButton: Color = Color(0xFFD9D9D9),
    val fontBlue: Color = Color(0xFF016DB7),
    val yellowRanking: Color = Color(0xFF9FA616),
    val orangeRanking: Color = Color(0xFFA65916),
    val seaSerpent: Color = Color(0xFF3AC2D8),
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors()
}

val Background = Color(0xFFF5F5F7)

val Primary = Color(0xFF0066FF)
val OnPrimary = Color.White

val TextPrimary = Color(0xFF111111)

val MainRed = Color(0xFF8D0000)
val Red = Color(0xFFF32409)
val MainBlue = Color(0xFF050C56)