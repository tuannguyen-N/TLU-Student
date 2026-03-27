package org.example.project.presentations.screen.gpa_predict

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.mapper.TranscriptMapper
import org.example.project.domain.model.SubjectScore
import org.example.project.domain.usecase.GpaPredictUseCase
import org.example.project.domain.usecase.ScheduleUseCase
import org.example.project.domain.usecase.SemesterUseCase
import org.example.project.domain.usecase.TranscriptUseCase

class GpaPredictViewModel(
    private val semesterUseCase: SemesterUseCase,
    private val transcriptUseCase: TranscriptUseCase,
    private val scheduleUseCase: ScheduleUseCase,
    private val gpaPredictUseCase: GpaPredictUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(GpaPredictState())
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch { loadTranscript() }
        viewModelScope.launch { loadSemester() }
    }

    private suspend fun loadSemester() {
        semesterUseCase.getSemesters().fold(
            onSuccess = { semesters ->
                val latest = semesters?.lastOrNull() ?: return@fold
                scheduleUseCase.getSemesterSubjects(latest.semesterName).fold(
                    onSuccess = {
                        updateState { copy(subjects = it) }
                    },
                    onFailure = {
                        Log.e("GPA Predict", "getSemesters: $it")
                    }
                )
            },
            onFailure = {
                Log.e("ExamViewModel", "getSemesters: Error $it")
            }
        )
    }

    private suspend fun loadTranscript() {
        transcriptUseCase.getTranscript().fold(
            onSuccess = {
                val gpa = TranscriptMapper.getGpa(it)
                val credits = TranscriptMapper.getTotalCredit(it)
                updateState { copy(realGpa = gpa, totalRealCredit = credits) }
            },
            onFailure = {
                Log.e("GPA Predict", "loadData: $it")
            }
        )
    }

    fun onMidtermChange(subjectCode: String, value: String) {
        updateState {
            val current = scores[subjectCode] ?: SubjectScore()
            copy(scores = scores + (subjectCode to current.copy(midterm = value)))
        }
    }

    fun onFinalChange(subjectCode: String, value: String) {
        updateState {
            val current = scores[subjectCode] ?: SubjectScore()
            copy(scores = scores + (subjectCode to current.copy(final = value)))
        }
    }

    fun onPredictGpa() {
        updateFailedSubject()
        val (predictGpa, totalPredictCredit) = gpaPredictUseCase.predictGpa(
            _uiState.value.scores,
            _uiState.value.realGpa,
            _uiState.value.totalRealCredit
        )
        updateState { copy(predictedGpa = predictGpa, totalPredictedCredit = totalPredictCredit) }
    }

    private fun updateFailedSubject() {
        val failedSubject = gpaPredictUseCase.getFailedSubject(_uiState.value.scores)
        updateState { copy(failedSubjects = failedSubject) }
    }

    fun onResetData() {
        updateState {
            copy(
                predictedGpa = null,
                totalPredictedCredit = null,
                scores = emptyMap(),
                failedSubjects = emptyList()
            )
        }
    }

    private fun updateState(block: GpaPredictState.() -> GpaPredictState) {
        _uiState.value = _uiState.value.block()
    }
}