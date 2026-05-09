package org.example.project.presentations.screen.gpa_tracker

import org.example.project.domain.model.TranscriptUiModel

data class GpaTrackerState(
    val transcript: TranscriptUiModel? = null,
    val isLoading: Boolean = false,
)
