package org.example.project.presentations.screen.chat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.example.project.presentations.screen.chat.components.ChatContent

@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    ChatContent(
        uiState = uiState,
        onBack = onBack,
        onPromptChange = viewModel::onPromptChange,
        onSendClick = viewModel::sendMessage
    )
}