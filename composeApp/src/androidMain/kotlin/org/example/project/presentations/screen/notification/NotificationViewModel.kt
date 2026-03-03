package org.example.project.presentations.screen.notification

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.project.domain.model.Notification
import org.example.project.domain.model.NotificationState
import org.example.project.domain.model.NotificationType

class NotificationViewModel : ViewModel() {
    private val allNotifications = listOf(
        Notification("Title 1", "Content 1", "Actor 1", NotificationType.DEPARTMENT, 1620972800),
        Notification("Title 1", "Content 1", "Actor 1", NotificationType.TEACHER, 1620972800),
        Notification("Title 1", "Content 1", "Actor 1", NotificationType.SCHOOL, 1620972800),
        Notification("Title 1", "Content 1", "Actor 1", NotificationType.DEPARTMENT, 1620972800)
    )

    private val _uiState = MutableStateFlow(NotificationState(notifications = allNotifications))
    val uiState = _uiState.asStateFlow()

    fun onTabSelected(index: Int) {
        val filtered = when (index) {
            0 -> allNotifications
            1 -> allNotifications.filter { it.type == NotificationType.SCHOOL }
            2 -> allNotifications.filter { it.type == NotificationType.TEACHER }
            else -> allNotifications.filter { it.type == NotificationType.DEPARTMENT }
        }

        _uiState.update {
            it.copy(
                selectedTab = index,
                notifications = filtered
            )
        }
    }
}