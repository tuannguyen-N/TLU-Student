package org.example.project.domain.utils

expect object DateTimeUtils {
    fun formatTime(timestamp: Long): String
    fun formatRelativeTime(timestamp: Long): String
}
