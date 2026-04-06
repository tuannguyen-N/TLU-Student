package org.example.project.presentations.screen.notification

import org.example.project.domain.model.NotificationUiModel

data class NotificationState(
    val selectedTab: Int = 0,
    val notifications: List<NotificationUiModel> = emptyList(),
    val filteredNotifications: List<NotificationUiModel> = emptyList(),
    val isShowBottomSheet: Boolean = false
)