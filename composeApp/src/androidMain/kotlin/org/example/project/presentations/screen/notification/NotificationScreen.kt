package org.example.project.presentations.screen.notification

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.presentations.screen.notification.components.HeaderContainer
import org.example.project.presentations.screen.notification.components.NotificationBottomSheetContent
import org.example.project.presentations.screen.notification.components.NotificationList
import org.example.project.presentations.screen.notification.components.NotificationTabs
import org.example.project.presentations.theme.LocalExtendedColors

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun NotificationScreen(
    onBack: () -> Unit = {}
) {
    val viewModel: NotificationViewModel = viewModel()
    val state by viewModel.uiState.collectAsState()

    var showBottomSheet by remember { mutableStateOf(false) }

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
                selectedTab = state.selectedTab,
                onTabSelected = viewModel::onTabSelected
            )

            NotificationList(
                notifications = state.notifications,
                modifier = Modifier.fillMaxHeight().background(LocalExtendedColors.current.background),
                onShowBottomSheet = {
                    showBottomSheet = true
                }
            )
        }

        if (showBottomSheet){
            ModalBottomSheet(
                onDismissRequest = {showBottomSheet  = false},
                dragHandle = null,
                shape = RoundedCornerShape(0,0,0,0),
            ) {
                NotificationBottomSheetContent(
                    onDismiss = {showBottomSheet = false}
                )
            }
        }
    }
}