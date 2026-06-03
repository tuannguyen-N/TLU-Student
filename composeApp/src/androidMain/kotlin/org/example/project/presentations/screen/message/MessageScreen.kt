package org.example.project.presentations.screen.message

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.presentations.screen.message.components.MessageContent
import org.example.project.presentations.screen.message.components.MessageInputBar
import org.example.project.presentations.screen.message.components.MessageTopBar
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun MessageScreen(
    viewModel: MessageViewModel,
    onBack: () -> Unit
) {
    val messages by viewModel.messages.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val chatUser by viewModel.chatUser.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            MessageTopBar(
                onBack = onBack,
                chatUser = chatUser
            )
        },
        bottomBar = {
            MessageInputBar(
                state = uiState,
                onMessageChange = viewModel::onMessageChange,
                onSend = viewModel::onSend,
                onImagePick = viewModel::onImageSelected,
                onRemoveImage = viewModel::onRemoveImage
            )
        }
    ) { innerPadding ->
        MessageContent(
            messages = messages,
            modifier = Modifier.padding(innerPadding)
        )
    }
}