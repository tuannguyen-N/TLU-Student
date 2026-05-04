package org.example.project.presentations.screen.notification_detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.project.data.remote.dto.notification_detail.NotificationDetailData
import org.example.project.domain.repository.NotificationRepository

class NotificationDetailViewModel(
    private val notificationRepository: NotificationRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id: Int = savedStateHandle["id"] ?: 0

    private val _notificationDetail = MutableStateFlow<NotificationDetailData?>(null)
    val notificationDetail: StateFlow<NotificationDetailData?> = _notificationDetail

    init {
        getNotificationDetail()
    }

    private fun getNotificationDetail() {
        viewModelScope.launch {
            notificationRepository.getNotificationDetail(id).onSuccess {
                _notificationDetail.value = it
            }.onFailure {
                Log.e("123123", "getNotificationDetail: $it", )
            }
        }
    }
}