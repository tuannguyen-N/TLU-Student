package org.example.project.presentations.screen.message

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.example.project.presentations.screen.message.components.MessageContent
import org.example.project.presentations.screen.message.components.MessageInputBar
import org.example.project.presentations.screen.message.components.MessageTopBar
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun MessageScreen(
    onBack: () -> Unit
) {
    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            MessageTopBar(
                onBack = onBack,
            )
        },
        bottomBar = { MessageInputBar() }
    ) { innerPadding ->
        MessageContent(
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MessageScreenPreview() {
    MaterialTheme {
        MessageScreen(onBack = {})
    }
}