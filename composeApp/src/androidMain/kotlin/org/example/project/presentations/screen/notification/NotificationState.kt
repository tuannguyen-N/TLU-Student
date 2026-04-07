package org.example.project.presentations.screen.notification

import org.example.project.domain.model.NotificationUiModel

data class NotificationState(
    val selectedTab: Int = 0,
    val filteredNotifications: List<NotificationUiModel> = emptyList(),
    val listFullNotifications: List<NotificationUiModel> = emptyList(),
    val newNotificationIds: Set<Int> = emptySet(),
    val isRefreshing: Boolean = false,
    val isShowBottomSheet: Boolean = false
)