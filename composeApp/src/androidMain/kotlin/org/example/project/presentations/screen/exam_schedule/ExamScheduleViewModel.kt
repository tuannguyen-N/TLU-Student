package org.example.project.presentations.screen.exam_schedule

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.mapper.toExamDays
import org.example.project.data.remote.dto.semester.Semester
import org.example.project.domain.model.ExamScheduleState
import org.example.project.domain.repository.ExamScheduleRepository
import org.example.project.domain.usecase.SemesterUseCase

class ExamScheduleViewModel(
    private val semesterUseCase: SemesterUseCase,
    private val examScheduleRepository: ExamScheduleRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ExamScheduleState())
    val uiState = _uiState.asStateFlow()

    init {
        observeSemesters()
        observeExamSchedule()
        initData()
    }

    private fun initData() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            getSemesters()
            updateState { copy(isLoading = false) }
        }
    }

    private suspend fun getSemesters() {
        semesterUseCase.getSemesters().fold(
            onSuccess = {
                updateState { copy(selectedSemester = it.last()) }
                getExamSchedule(it.last().semesterName)
            },
            onFailure = {
                Log.e("ExamViewModel", "getSemesters: Error $it")
            }
        )
    }

    private suspend fun getExamSchedule(semester: String) {
        examScheduleRepository.getExamSchedules(semester).fold(
            onSuccess = {
                // TODO:  Update state
            },
            onFailure = {
                Log.e("ExamViewModel", "getExamSchedule: Error $it")
            }
        )
    }

    private fun observeExamSchedule() {
        examScheduleRepository.examSchedules.onEach {
            updateState { copy(examDays = it.toExamDays()) }
        }.launchIn(viewModelScope)
    }

    private fun observeSemesters() {
        semesterUseCase.semesters.onEach { semesters ->
            updateState { copy(semesters = semesters) }
        }.launchIn(viewModelScope)
    }

    fun onTabSelected(tab: Int) {
        updateState { copy(selectedTab = tab) }
    }

    fun onToggleDropdown() {
        updateState { copy(isDropdownExpanded = !isDropdownExpanded) }
    }

    fun onSemesterChanged(semester: Semester) {
        viewModelScope.launch {
            updateState { copy(selectedSemester = semester, isLoading = true) }
            getExamSchedule(semester.semesterName)
            updateState { copy(isLoading = false) }
        }
    }

    private fun updateState(newState: ExamScheduleState.() -> ExamScheduleState) {
        _uiState.update { it.newState() }
    }
}