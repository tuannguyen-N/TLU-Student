package org.example.project.presentations.screen.notification

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.model.NotificationSender
import org.example.project.domain.model.NotificationType
import org.example.project.domain.repository.NotificationRepository

class NotificationViewModel(
    private val notificationRepository: NotificationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(NotificationState())
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData(){
        viewModelScope.launch {
            notificationRepository.getNotifications().fold(
                onSuccess = {result ->
                    updateState { copy(notifications = result, filteredNotifications = result) }
                },
                onFailure = {
                    Log.e("NotificationViewModel", "loadData: $it", )
                }
            )
        }
    }

    fun onTabSelected(index: Int) {
        val allNotifications = _uiState.value.notifications
        val filtered = when (index) {
            0 -> allNotifications
            1 -> allNotifications.filter { it.sender == NotificationSender.SYSTEM }
            2 -> allNotifications.filter { it.sender == NotificationSender.LECTURER }
            else -> allNotifications.filter { it.sender == NotificationSender.FACULTY }
        }

        _uiState.update {
            it.copy(
                selectedTab = index,
                filteredNotifications = filtered
            )
        }
    }

    fun onShowBottomSheet() {
        updateState { copy(isShowBottomSheet = true) }
    }

    fun onHideBottomSheet() {
        updateState { copy(isShowBottomSheet = false) }
    }

    private fun updateState(block: NotificationState.() -> NotificationState) {
        _uiState.update(block)
    }
}