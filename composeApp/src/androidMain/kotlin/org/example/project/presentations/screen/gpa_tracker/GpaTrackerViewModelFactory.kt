package org.example.project.presentations.screen.gpa_tracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.usecase.TranscriptUseCase

class GpaTrackerViewModelFactory(
    private val transcriptUseCase: TranscriptUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(GpaTrackerViewModel::class.java)) {
            return GpaTrackerViewModel(transcriptUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}