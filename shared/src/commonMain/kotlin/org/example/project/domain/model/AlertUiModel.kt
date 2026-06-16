package org.example.project.domain.model

data class AlertUiModel(
    val id: Int,
    val title: String,
    val content: String,
    val severity: NotificationSeverity,
    val notificationReferenceType: NotificationReferenceType,
    val deadline: String,
    val daysUntil: Long
)