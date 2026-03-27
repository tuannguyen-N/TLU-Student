package org.example.project.presentations.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color


data class ExtendedColors(
    val background: Color = Color(0xFFF5F5F7),
    val primary: Color = Color(0xFF0066FF),
    val onPrimary: Color = Color(0xFFFFFFFF),

    val mainBlue: Color = Color(0xFF050C56),
    val mainRed: Color = Color(0xFF8D0000),
    val red: Color = Color(0xFFF32409),
    val redLight: Color = Color(0xFFFFD9D4),
    val midBlue: Color = Color(0xFF0623A4),
    val lightBlue: Color = Color(0xFF0044FF),

    val textPrimary: Color = Color(0xFF111111),
    val white: Color = Color(0xFFFFFFFF),

    val yellow: Color = Color(0xFFFFA400),
    val lightYellow: Color = Color(0xFFFFF0D6),

    val green: Color = Color(0xFF16A634),
    val lightGreen: Color = Color(0xFFDAFFE2),

    val gray: Color = Color(0xFF848484),
    val lightGray: Color = Color(0xFFD9D9D9),
    val grayNavy: Color = Color(0xFF64748B),

    val fontBlue: Color = Color(0xFF016DB7),

    val purple: Color = Color(0xFF7C3AED),
    val orange: Color = Color(0xFFF97416),
    val seaSerpent: Color = Color(0xFF3AC2D8),

    val cardBackground: Color = Color(0xFFF1F4F5),
    val blackBackground: Color = Color(0xFF0C0F10),

    val yellowRanking: Color = Color(0xFFEAB308),
    val average: Color = Color(0xFFF97316)
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors()
}