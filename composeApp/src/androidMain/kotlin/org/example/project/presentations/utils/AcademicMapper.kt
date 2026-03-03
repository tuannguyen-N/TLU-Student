package org.example.project.presentations.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.example.project.domain.model.AcademicRank
import org.example.project.presentations.theme.LocalExtendedColors

fun Double.toAcademicRank(): AcademicRank {
    return when {
        this >= 3.6 -> AcademicRank.EXCELLENT
        this >= 2.5 -> AcademicRank.GOOD
        this >= 2.0 -> AcademicRank.AVERAGE
        else -> AcademicRank.WEAK
    }
}

@Composable
fun AcademicRank.toColor(): Color = when (this) {
    AcademicRank.EXCELLENT -> LocalExtendedColors.current.green
    AcademicRank.GOOD -> LocalExtendedColors.current.yellowRanking
    AcademicRank.AVERAGE -> LocalExtendedColors.current.orangeRanking
    AcademicRank.WEAK -> LocalExtendedColors.current.red
}

fun Double.toTextRank(): String = when {
    this >= 3.6 -> "Giỏi"
    this >= 2.5 -> "Khá"
    this >= 2.0 -> "Trung bình"
    else -> "Yếu"
}

fun Double.toTextTermRank(): String = when {
    this >= 3.6 -> "Giỏi"
    this >= 2.5 -> "Khá"
    this >= 2.0 -> "Đạt"
    else -> "Không đạt"
}

fun Double.isPass(): Boolean = this >= 2.0