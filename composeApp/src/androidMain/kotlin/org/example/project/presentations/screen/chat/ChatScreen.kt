package org.example.project.presentations.screen.chat

import androidx.compose.runtime.Composable
import org.example.project.presentations.screen.chat.components.ChatContent

@Composable
fun ChatScreen(
    onBack: () -> Unit
) {
    ChatContent(
        onBack = onBack
    )
}