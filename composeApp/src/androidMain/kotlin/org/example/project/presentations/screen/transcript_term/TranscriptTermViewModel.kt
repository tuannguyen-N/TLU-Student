package org.example.project.presentations.screen.transcript_term

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.model.TranscriptTermState
import org.example.project.domain.usecase.TranscriptUseCase

class TranscriptTermViewModel(
    savedStateHandle: SavedStateHandle,
    private val transcriptUseCase: TranscriptUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TranscriptTermState())
    val uiState = _uiState.asStateFlow()

    private val semesterLabel: String = checkNotNull(savedStateHandle["semesterLabel"])

    init {
        loadSemesterData()
    }

    private fun loadSemesterData() {
        viewModelScope.launch {
            transcriptUseCase.transcriptCached
                .filterNotNull()
                .collect { transcriptUiModel ->
                    val semester = transcriptUiModel.academicYearGroups
                        .flatMap { it.semesters }
                        .firstOrNull { it.semesterLabel == semesterLabel }

                    semester?.let {
                        _uiState.update { _ ->
                            TranscriptTermState(
                                semesterLabel = it.semesterLabel,
                                semesterGpa = it.semesterGpa,
                                creditsPassed = it.creditsPassed,
                                subjects = it.subjects,
                                academicYear = it.academicYear
                            )
                        }
                    }
                }
        }
    }
}