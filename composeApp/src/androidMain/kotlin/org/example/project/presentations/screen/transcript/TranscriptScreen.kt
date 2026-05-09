package org.example.project.presentations.screen.transcript

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.domain.model.SemesterUiModel
import org.example.project.presentations.screen.transcript.components.TranscriptContent

@Composable
fun TranscriptScreen(
    viewModel: TranscriptViewModel,
    onOpenNotificationScreen: () -> Unit = {},
    onOpenTranscriptTerm: (SemesterUiModel) -> Unit = {},
    onOpenChat: () -> Unit = {},
    onOpenGpaTracker: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val transcriptUiModel = uiState.transcriptUiModel

    TranscriptContent(
        onOpenNotificationScreen,
        uiState,
        transcriptUiModel,
        onOpenTranscriptTerm,
        onOpenChat,
        viewModel::refreshData,
        onOpenGpaTracker
    )
}