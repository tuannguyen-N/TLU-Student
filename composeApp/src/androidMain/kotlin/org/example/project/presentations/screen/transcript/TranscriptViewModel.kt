package org.example.project.presentations.screen.transcript

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.example.project.data.mapper.TranscriptMapper
import org.example.project.data.mapper.TranscriptMapper.toUiModel
import org.example.project.domain.model.TranscriptState
import org.example.project.domain.usecase.TranscriptUseCase

class TranscriptViewModel(
    private val transcriptUseCase: TranscriptUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TranscriptState())
    val uiState = _uiState.asStateFlow()

    init {
        observeTranscript()
        loadData()
    }

    private fun observeTranscript() {
        transcriptUseCase.transcriptCached
            .filterNotNull()
            .onEach { uiModel ->
                updateState { copy(transcriptUiModel = uiModel) }
            }
            .launchIn(viewModelScope)
    }

    private fun loadData() {
        viewModelScope.launch {
            updateState { copy(isLoading = true, error = null) }
            transcriptUseCase.getTranscript().fold(
                onSuccess = {
                    updateState {
                        copy(
                            isLoading = false,
                            gpa = TranscriptMapper.getGpa(it),
                            totalCredit = TranscriptMapper.getTotalCredit(it)
                        )
                    }
                },
                onFailure = { error ->
                    Log.e("123123", "loadData: $error")
                    updateState { copy(isLoading = false, error = error.message) }
                }
            )
        }
    }

    private fun updateState(block: TranscriptState.() -> TranscriptState) {
        _uiState.value = _uiState.value.block()
    }
}