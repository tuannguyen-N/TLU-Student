package org.example.project.presentations.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Long.toFormatTime(): String {
    val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault())

    return Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .format(formatter)
}