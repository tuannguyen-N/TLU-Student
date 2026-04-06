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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.example.project.presentations.screen.notification.NotificationState
import org.example.project.presentations.screen.notification.NotificationViewModel
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun NotificationContent(
    onBack: () -> Unit,
    uiState: NotificationState,
    viewModel: NotificationViewModel
) {
    Scaffold(
        contentWindowInsets = WindowInsets(0),
        topBar = {
            HeaderContainer(onBack = onBack)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
        ) {
            NotificationTabs(
                selectedTab = uiState.selectedTab,
                onTabSelected = viewModel::onTabSelected
            )

            NotificationList(
                notifications = uiState.filteredNotifications,
                modifier = Modifier
                    .fillMaxHeight()
                    .background(LocalExtendedColors.current.background),
                onShowBottomSheet = viewModel::onShowBottomSheet
            )
        }

        if (uiState.isShowBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = viewModel::onHideBottomSheet,
                dragHandle = null,
                shape = RoundedCornerShape(0, 0, 0, 0),
            ) {
                NotificationBottomSheetContent(
                    onDismiss = viewModel::onHideBottomSheet
                )
            }
        }
    }
}