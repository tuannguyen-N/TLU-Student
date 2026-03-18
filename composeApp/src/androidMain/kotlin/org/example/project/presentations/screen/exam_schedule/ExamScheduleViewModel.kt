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
import kotlinx.datetime.LocalDate
import org.example.project.data.mapper.toExamDays
import org.example.project.data.remote.dto.semester.Semester
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
            onSuccess = { semesters ->
                val latest = semesters?.lastOrNull() ?: return@fold
                updateState { copy(selectedSemester = latest, currentSemester = latest) }
                getExamSchedule(latest.semesterName)
            },
            onFailure = {
                Log.e("ExamViewModel", "getSemesters: Error $it")
                updateState { copy(error = it.message) }
            }
        )
    }

    private suspend fun getExamSchedule(semester: String) {
        examScheduleRepository.getExamSchedules(semester).fold(
            onSuccess = { /* data flows through observeExamSchedule */ },
            onFailure = {
                Log.e("ExamViewModel", "getExamSchedule: Error $it")
                updateState { copy(error = it.message) }
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
            val selected = semesters?.lastOrNull()
            updateState {
                copy(
                    semesters = semesters.orEmpty(),
                    selectedSemester = selected,
                )
            }
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

    fun onChangeDate(date: LocalDate) {
        updateState {
            val examDay = examDays.find { it.localExamDay == date }
            copy(selectedDate = date, examDay = examDay)
        }
    }

    fun onResetData(){
        updateState { copy(resetTrigger = resetTrigger + 1) }
        initData()
    }

    private fun updateState(newState: ExamScheduleState.() -> ExamScheduleState) {
        _uiState.update { it.newState() }
    }
}