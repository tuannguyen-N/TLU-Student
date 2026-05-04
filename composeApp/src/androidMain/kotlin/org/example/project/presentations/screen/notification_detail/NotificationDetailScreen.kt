package org.example.project.presentations.screen.notification_detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.presentations.screen.notification_detail.components.NotificationDetailContent

@Composable
fun NotificationDetailScreen(
    onBack: () -> Unit,
    viewModel: NotificationDetailViewModel
) {
    val notificationDetail by viewModel.notificationDetail.collectAsStateWithLifecycle()
    NotificationDetailContent(notificationDetail, onBack)
}