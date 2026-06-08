package org.example.project.presentations.screen.notification

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.model.NotificationSender
import org.example.project.domain.model.NotificationUiModel
import org.example.project.domain.repository.NotificationRepository

class NotificationViewModel(
    private val notificationRepository: NotificationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(NotificationState())
    val uiState = _uiState.asStateFlow()

    val filteredNotifications = combine(
        notificationRepository.notifications,
        uiState.map { it.selectedTab }
    ) { notifications, tab ->
        when (tab) {
            1 -> notifications.filter { it.sender == NotificationSender.SYSTEM }
            2 -> notifications.filter { it.sender == NotificationSender.LECTURER }
            3 -> notifications.filter { it.sender == NotificationSender.FACULTY }
            else -> notifications
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        loadData(forceRefresh = true)
    }

    private fun loadData(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            val page = if (forceRefresh) 0 else _uiState.value.currentPage
            notificationRepository.getNotifications(
                forceRefresh = forceRefresh,
                page = page
            ).onSuccess { hasMore ->
                Log.e("NotificationViewModel", "load_data: $hasMore", )
                updateState {
                    copy(
                        currentPage = if (forceRefresh) 1 else currentPage + 1,
                        hasMore = hasMore
                    )
                }
            }
        }
    }

    fun onLoadMore() {
        val state = _uiState.value
        if (state.isLoadingMore || !state.hasMore) return

        viewModelScope.launch {
            updateState { copy(isLoadingMore = true) }
            try {
                val currentPage = _uiState.value.currentPage
                notificationRepository.getNotifications(
                    forceRefresh = false,
                    page = currentPage
                ).onSuccess { hasMore ->
                    Log.e("NotificationViewModel", "onLoadMore: $hasMore", )
                    updateState {
                        copy(
                            currentPage = currentPage + 1,
                            hasMore = hasMore
                        )
                    }
                }
            } finally {
                updateState { copy(isLoadingMore = false) }
            }
        }
    }

    fun onTabSelected(index: Int) {
        updateState { copy(selectedTab = index) }
    }

    fun onMarkAllRead() {
        viewModelScope.launch {
            notificationRepository.insertReadNotifications(filteredNotifications.value)
        }
    }

    fun onRefreshData() {
        viewModelScope.launch {
            updateState { copy(isRefreshing = true) }
            try {
                loadData(forceRefresh = true)
            } finally {
                delay(1000L)
                updateState { copy(isRefreshing = false) }
            }
        }
    }

    fun onRead(notification: NotificationUiModel) {
        if (notification.isRead) return

        viewModelScope.launch {
            notificationRepository.insertReadNotification(notification)
        }
    }

    fun onShowBottomSheet() {
        updateState { copy(isShowBottomSheet = true) }
    }

    fun onHideBottomSheet() {
        updateState { copy(isShowBottomSheet = false) }
    }

    private fun updateState(newState: NotificationState.() -> NotificationState) {
        _uiState.update(newState)
    }
}