package org.example.project.presentations.screen.gpa_tracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.model.AppResult
import org.example.project.domain.model.ExportState
import org.example.project.domain.usecase.TranscriptUseCase

class GpaTrackerViewModel(
    private val transcriptUseCase: TranscriptUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(GpaTrackerState())
    val uiState = _uiState.asStateFlow()

    init {
        loadTranscript()
        observeTranscript()
    }

    private fun observeTranscript() {
        transcriptUseCase.transcriptCached.onEach {
            updateState { copy(transcript = it) }
        }.launchIn(viewModelScope)
    }

    private fun loadTranscript() {
        viewModelScope.launch {
            transcriptUseCase.getTranscript(true)
        }
    }

    fun exportTranscript() {
        viewModelScope.launch {
            updateState { copy(exportState = ExportState.Loading) }
            when (val result = transcriptUseCase.exportTranscript()) {
                is AppResult.Success -> updateState { copy(exportState = ExportState.Success(result.data)) }
                is AppResult.Failure -> updateState {
                    copy(
                        exportState = ExportState.Error(
                            result.message ?: "Export thất bại"
                        )
                    )
                }
            }
        }
    }

    fun resetExportState() {
        updateState { copy(exportState = ExportState.Idle) }
    }

    private fun updateState(newState: GpaTrackerState.() -> GpaTrackerState) {
        _uiState.update(newState)
    }
}