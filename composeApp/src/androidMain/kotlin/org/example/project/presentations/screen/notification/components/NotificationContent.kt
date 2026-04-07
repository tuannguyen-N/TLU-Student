package org.example.project.presentations.screen.notification.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.example.project.domain.model.NotificationUiModel
import org.example.project.presentations.screen.notification.NotificationState
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NotificationContent(
    uiState: NotificationState,
    onBack: () -> Unit,
    onRead: (NotificationUiModel) -> Unit,
    onShowBottomSheet: () -> Unit,
    onHideBottomSheet: () -> Unit,
    onRefresh: () -> Unit,
    onTabSelected: (Int) -> Unit,
    onMarkAllRead: () -> Unit
) {
    val pullRefreshState = rememberPullToRefreshState()
    val color = LocalExtendedColors.current
    Scaffold(
        contentWindowInsets = WindowInsets(0),
        topBar = {
            HeaderContainer(
                onBack = onBack,
                onMarkAllRead = onMarkAllRead
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
        ) {
            NotificationTabs(
                color = color,
                selectedTab = uiState.selectedTab,
                onTabSelected = onTabSelected,
                notifications = uiState.listFullNotifications
            )
            PullToRefreshBox(
                isRefreshing = uiState.isRefreshing,
                onRefresh = onRefresh,
                state = pullRefreshState
            ) {
                NotificationList(
                    color = color,
                    notifications = uiState.filteredNotifications,
                    newNotificationIds = uiState.newNotificationIds,
                    selectedTab = uiState.selectedTab,
                    onShowBottomSheet = onShowBottomSheet,
                    onRead = onRead,
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(LocalExtendedColors.current.background)
                )
            }
        }

        if (uiState.isShowBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = onHideBottomSheet,
                dragHandle = null,
                shape = RoundedCornerShape(0, 0, 0, 0),
            ) {
                NotificationBottomSheetContent(
                    onDismiss = onHideBottomSheet,
                )
            }
        }
    }
}