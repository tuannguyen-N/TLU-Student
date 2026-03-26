package org.example.project.presentations.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


data class ExtendedColors(
    val background: Color = Background,
    val mainRed: Color = MainRed,
    val mainBlue: Color = MainBlue,
    val red: Color = Red,
    val redLight: Color = Color(0xFFFFD9D4),
    val textPrimary: Color = TextPrimary,
    val blackBackground: Color = Color(0xFF0C0F10),
    val primary: Color = Primary,
    val lightBlue: Color = Color(0xFF0044ff),
    val onPrimary: Color = OnPrimary,
    val white: Color = Color.White,
    val yellow: Color = Color(0xFFFFA400),
    val lightYellow: Color = Color(0xFFFFF0D6),
    val green: Color = Color(0xFF16A634),
    val lightGreen: Color = Color(0xFFDAFFE2),
    val gray: Color = Color(0xFF848484),
    val grayButton: Color = Color(0xFFD9D9D9),
    val fontBlue: Color = Color(0xFF016DB7),
    val yellowRanking: Color = Color(0xFFEAB308),
    val average: Color = Color(0xFFF97316),
    val purple: Color = Color(0xFF7C3AED),
    val orange: Color = Color(0xFFf97416),
    val seaSerpent: Color = Color(0xFF3AC2D8),
    val grayNavy: Color = Color(0xFF64748B),
    val cardBackground: Color = Color(0xFFF1F4F5)
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