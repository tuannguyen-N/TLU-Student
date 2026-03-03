package org.example.project.domain.model

data class Notification (
    val title: String,
    val content: String,
    val actor: String,
    val type: NotificationType,
    val notificationTime: Long,
    val isRead: Boolean = false
)