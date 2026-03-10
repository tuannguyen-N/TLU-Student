package org.example.project.presentations.screen.transcript

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.usecase.TranscriptUseCase

class TranscriptViewModelFactory(
    private val transcriptUseCase: TranscriptUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(TranscriptViewModel::class.java)) {
            return TranscriptViewModel(transcriptUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}