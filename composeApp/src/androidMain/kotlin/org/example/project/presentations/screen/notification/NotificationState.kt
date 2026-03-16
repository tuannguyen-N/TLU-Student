package org.example.project.presentations.screen.notification

import org.example.project.domain.model.Notification

data class NotificationState(
    val selectedTab: Int = 0,
    val notifications: List<Notification>
)