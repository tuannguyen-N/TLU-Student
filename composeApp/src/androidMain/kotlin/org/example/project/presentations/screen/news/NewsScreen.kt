package org.example.project.presentations.screen.news

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.screen.news.components.NewsContent

@Composable
fun NewsScreen(
    viewModel: NewsViewModel,
    onBack: () -> Unit,
    onOpenNews: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    StatusBarStyle(darkIcons = true)
    NewsContent(
        uiState,
        onBack = onBack,
        onOpenNews = onOpenNews
    )
}