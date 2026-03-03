package org.example.project.domain.model

data class NotificationState(
    val selectedTab: Int = 0,
    val notifications: List<Notification>
)