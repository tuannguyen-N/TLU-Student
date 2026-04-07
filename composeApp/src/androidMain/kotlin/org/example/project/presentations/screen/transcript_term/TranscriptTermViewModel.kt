package org.example.project.presentations.screen.transcript_term

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.usecase.TranscriptUseCase

class TranscriptTermViewModel(
    savedStateHandle: SavedStateHandle,
    private val transcriptUseCase: TranscriptUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TranscriptTermState())
    val uiState = _uiState.asStateFlow()

    private val semesterLabel: String = checkNotNull(savedStateHandle["semesterLabel"])
    private val academicYear: String = checkNotNull(savedStateHandle["academicYear"])

    init {
        observeTranscript()
    }

    private fun observeTranscript() {
        transcriptUseCase.transcriptCached
            .filterNotNull()
            .onEach { transcriptUiModel ->
                val semester = transcriptUiModel.academicYearGroups
                    .flatMap { it.semesters }
                    .firstOrNull {
                        it.semesterLabel == semesterLabel &&
                                it.academicYear == academicYear
                    }

                semester?.let {
                    _uiState.update { transcriptTermState ->
                        transcriptTermState.copy(
                            semesterLabel = it.semesterLabel,
                            semesterGpa = it.semesterGpa,
                            creditsPassed = it.creditsPassed,
                            subjects = it.subjects,
                            academicYear = it.academicYear
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun refreshData() {
        viewModelScope.launch {
            val startTime = System.currentTimeMillis()

            _uiState.update { it.copy(isRefreshing = true) }

            try {
                transcriptUseCase.getTranscript(forceRefresh = true)
            } finally {
                val elapsed = System.currentTimeMillis() - startTime
                val minTime = 500L

                if (elapsed < minTime) {
                    delay(minTime - elapsed)
                }

                _uiState.update { it.copy(isRefreshing = false) }
            }
        }
    }
}