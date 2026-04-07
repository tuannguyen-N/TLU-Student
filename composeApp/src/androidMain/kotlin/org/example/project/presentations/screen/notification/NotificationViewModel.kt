package org.example.project.presentations.screen.notification

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    private var clearJob: Job? = null

    init {
        observeNotifications()
        loadData()
    }

    private fun observeNotifications() {
        notificationRepository.notifications
            .onEach { newList ->
                val oldIds = _uiState.value.listFullNotifications.map { it.id }.toSet()
                val newIds = newList.map { it.id }.toSet() - oldIds
                val selectedTab = _uiState.value.selectedTab
                val filtered = filterNotifications(newList, selectedTab)

                updateState {
                    copy(
                        listFullNotifications = newList,
                        filteredNotifications = filtered,
                        newNotificationIds = newIds
                    )
                }

                if (newIds.isNotEmpty()) clearNewIdsLater()
            }
            .launchIn(viewModelScope)
    }

    fun onTabSelected(index: Int) {
        updateState {
            copy(
                selectedTab = index,
                filteredNotifications = filterNotifications(
                    _uiState.value.listFullNotifications,
                    index
                ),
                newNotificationIds = emptySet()
            )
        }
    }

    fun onMarkAllRead() {
        viewModelScope.launch {
            notificationRepository.insertReadNotifications(_uiState.value.filteredNotifications)
        }
    }

    private fun clearNewIdsLater() {
        clearJob?.cancel()
        clearJob = viewModelScope.launch {
            delay(2000)
            updateState { copy(newNotificationIds = emptySet()) }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            delay(200L)
            notificationRepository.getNotifications().onFailure {
                Log.e("NotificationViewModel", "loadData: $it")
            }
        }
    }

    private fun filterNotifications(
        notifications: List<NotificationUiModel>,
        tabIndex: Int
    ): List<NotificationUiModel> = when (tabIndex) {
        1 -> notifications.filter { it.sender == NotificationSender.SYSTEM }
        2 -> notifications.filter { it.sender == NotificationSender.LECTURER }
        3 -> notifications.filter { it.sender == NotificationSender.FACULTY }
        else -> notifications
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