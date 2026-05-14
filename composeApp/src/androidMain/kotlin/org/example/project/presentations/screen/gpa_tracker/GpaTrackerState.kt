package org.example.project.presentations.screen.gpa_tracker

import org.example.project.domain.model.ExportState
import org.example.project.domain.model.TranscriptUiModel

data class GpaTrackerState(
    val transcript: TranscriptUiModel? = null,
    val exportState: ExportState = ExportState.Idle,
    val isLoading: Boolean = false,
)