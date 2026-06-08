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

    val notifications = notificationRepository.notifications
    val filteredNotifications =
        combine(
            notifications,
            uiState.map { it.selectedTab }
        ) { notifications, tab ->

            when (tab) {
                1 -> notifications.filter {
                    it.sender == NotificationSender.SYSTEM
                }

                2 -> notifications.filter {
                    it.sender == NotificationSender.LECTURER
                }

                3 -> notifications.filter {
                    it.sender == NotificationSender.FACULTY
                }

                else -> notifications
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    init {
        loadData()
    }

    fun onTabSelected(index: Int) {
        updateState { copy(selectedTab = index) }
    }

    fun onMarkAllRead() {
        viewModelScope.launch {
            notificationRepository.insertReadNotifications(filteredNotifications.value)
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            delay(50L)
            notificationRepository.getNotifications(true).onFailure {
                Log.e("NotificationViewModel", "loadData: $it")
            }
        }
    }

    fun onRefreshData() {
        viewModelScope.launch {
            updateState { copy(isRefreshing = true) }
            try {
                notificationRepository.getNotifications(true)
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