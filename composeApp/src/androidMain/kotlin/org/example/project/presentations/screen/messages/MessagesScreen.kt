package org.example.project.presentations.screen.messages

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.R
import org.example.project.presentations.components.AppTopBar
import org.example.project.presentations.screen.messages.components.MessagesContent
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun MessagesScreen(
    viewModel: MessagesViewModel,
    onOpenNotificationScreen: () -> Unit,
    onOpenMessage: (studentId: String, chatName: String) -> Unit,
    onOpenStudentSearch: () -> Unit
) {
    val conversations by viewModel.conversations.collectAsStateWithLifecycle()
    val users by viewModel.users.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            AppTopBar(
                iconRes = R.drawable.icon_message,
                title = "Tin nhắn",
                onOpenNotificationScreen = onOpenNotificationScreen,
                isNotificationBadgeVisible = true,
                onSearch = onOpenStudentSearch,
                isShowSearch = true,
                backgroundColor = Color.White
            )
        },
        containerColor = LocalExtendedColors.current.white,
        contentWindowInsets = WindowInsets(0)
    ) { innerPadding ->
        MessagesContent(
            modifier = Modifier.padding(innerPadding),
            conversations = conversations,
            onOpenMessage = onOpenMessage,
            users = users
        )
    }
}