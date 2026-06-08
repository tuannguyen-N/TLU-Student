package org.example.project.presentations.screen.notification

data class NotificationState(
    val selectedTab: Int = 0,
    val isRefreshing: Boolean = false,
    val isLoadingMore: Boolean = false,
    val hasMore: Boolean = true,
    val currentPage: Int = 0,
    val isShowBottomSheet: Boolean = false
)