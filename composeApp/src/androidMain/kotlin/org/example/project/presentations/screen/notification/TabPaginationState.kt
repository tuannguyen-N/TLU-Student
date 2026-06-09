package org.example.project.presentations.screen.notification

data class TabPaginationState(
    val currentPage: Int = 0,
    val hasMore: Boolean = true,
    val isLoadingMore: Boolean = false
)