package org.example.project.presentations.screen.notification

data class NotificationState(
    val selectedTab: Int = 0,
    val isRefreshing: Boolean = false,
    val isShowBottomSheet: Boolean = false
)