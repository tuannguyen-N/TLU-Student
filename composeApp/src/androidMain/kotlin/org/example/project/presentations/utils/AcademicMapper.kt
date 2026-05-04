package org.example.project.presentations.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.example.project.domain.model.AcademicRank
import org.example.project.presentations.theme.LocalExtendedColors

fun Double.toAcademicRank(): AcademicRank {
    return when {
        this >= 9.0 -> AcademicRank.EXCELLENT
        this >= 8.0 -> AcademicRank.VERY_GOOD
        this >= 6.5 -> AcademicRank.GOOD
        this >= 5.0 -> AcademicRank.AVERAGE
        else -> AcademicRank.WEAK
    }
}

@Composable
fun AcademicRank.toColor(): Color = when (this) {
    AcademicRank.EXCELLENT -> LocalExtendedColors.current.purple
    AcademicRank.VERY_GOOD -> LocalExtendedColors.current.green
    AcademicRank.GOOD -> LocalExtendedColors.current.yellowRanking
    AcademicRank.AVERAGE -> LocalExtendedColors.current.average
    AcademicRank.WEAK -> LocalExtendedColors.current.red
}

fun Double.toTextRank(): String = when {
    this >= 9.0 -> "Xuất sắc"
    this >= 8.0 -> "Giỏi"
    this >= 6.5 -> "Khá"
    this >= 5.0 -> "Trung bình"
    else -> "Yếu"
}

fun Double.toTextTermRank(): String = when {
    this >= 9.0 -> "Xuất sắc"
    this >= 8.0 -> "Giỏi"
    this >= 6.5 -> "Khá"
    this >= 5.0 -> "Đạt"
    else -> "Không đạt"
}

fun Double.isPass(): Boolean = this >= 2.0