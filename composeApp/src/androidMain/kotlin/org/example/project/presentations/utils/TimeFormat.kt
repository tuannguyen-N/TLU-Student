package org.example.project.presentations.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

fun Long.toFormatTime(): String {
    val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault())

    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .format(formatter)
}

val today = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

fun getDateOfWeek(dateString: String = today, pattern: String = "dd/MM/yyyy"): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val date = LocalDate.parse(dateString, formatter)
    return date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("vi", "VN"))
}