package org.example.project.presentations.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object DateTimeUtils {
    @SuppressLint("ConstantLocale")
    private val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
    
    @SuppressLint("ConstantLocale")
    private val relativeDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    fun formatTime(timestamp: Long): String {
        return timeFormat.format(Date(timestamp))
    }

    fun formatRelativeTime(timestamp: Long): String {
        val calendar = Calendar.getInstance()
        val today = calendar.get(Calendar.DAY_OF_YEAR)
        val year = calendar.get(Calendar.YEAR)
        
        val messageCalendar = Calendar.getInstance()
        messageCalendar.timeInMillis = timestamp
        val msgDay = messageCalendar.get(Calendar.DAY_OF_YEAR)
        val msgYear = messageCalendar.get(Calendar.YEAR)
        
        return when {
            year == msgYear && today == msgDay -> {
                formatTime(timestamp)
            }
            year == msgYear && today - msgDay == 1 -> {
                "Hôm qua"
            }
            year == msgYear && today - msgDay < 7 && today - msgDay > 0 -> {
                val dayOfWeek = messageCalendar.get(Calendar.DAY_OF_WEEK)
                getDayOfWeekString(dayOfWeek)
            }
            else -> {
                relativeDateFormat.format(Date(timestamp))
            }
        }
    }

    private fun getDayOfWeekString(dayOfWeek: Int): String {
        return when (dayOfWeek) {
            Calendar.SUNDAY -> "Chủ Nhật"
            Calendar.MONDAY -> "Thứ Hai"
            Calendar.TUESDAY -> "Thứ Ba"
            Calendar.WEDNESDAY -> "Thứ Tư"
            Calendar.THURSDAY -> "Thứ Năm"
            Calendar.FRIDAY -> "Thứ Sáu"
            Calendar.SATURDAY -> "Thứ Bảy"
            else -> ""
        }
    }

    fun formatLastSeen(lastSeen: Long): String? {
        if (lastSeen == 0L) return null
        val diff = System.currentTimeMillis() - lastSeen
        val minutes = diff / 60_000
        val hours = diff / 3_600_000
        return when {
            minutes < 1 -> "vừa xong"
            minutes < 60 -> "$minutes phút"
            hours < 24 -> "$hours giờ"
            else -> null
        }
    }

    fun formatLastSeenTopBar(lastSeen: Long): String? {
        if (lastSeen == 0L) return null
        val diff = System.currentTimeMillis() - lastSeen
        val minutes = diff / 60_000
        val hours = diff / 3_600_000
        return when {
            minutes < 1 -> "gần đây"
            minutes < 60 -> "$minutes phút trước"
            hours < 24 -> "$hours giờ trước"
            else -> null
        }
    }
}
