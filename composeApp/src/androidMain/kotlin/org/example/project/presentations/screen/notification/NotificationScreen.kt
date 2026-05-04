package org.example.project.presentations.screen.notification

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.screen.notification.components.NotificationContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    viewModel: NotificationViewModel,
    onOpenNotificationDetail: (Int) -> Unit,
    onBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    StatusBarStyle(darkIcons = true)

    NotificationContent(
        uiState = uiState,
        onBack = onBack,
        onClickNotification = {
            viewModel.onRead(it)
            onOpenNotificationDetail(it.id)
        },
        onShowBottomSheet = viewModel::onShowBottomSheet,
        onRefresh = viewModel::onRefreshData,
        onTabSelected = viewModel::onTabSelected,
        onMarkAllRead = viewModel::onMarkAllRead
    )
}