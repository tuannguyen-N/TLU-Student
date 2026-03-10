package org.example.project.presentations.screen.transcript_term

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import org.example.project.domain.usecase.TranscriptUseCase

class TranscriptTermViewModelFactory(
    private val transcriptUseCase: TranscriptUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        val savedStateHandle = extras.createSavedStateHandle()
        if (modelClass.isAssignableFrom(TranscriptTermViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TranscriptTermViewModel(savedStateHandle, transcriptUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}