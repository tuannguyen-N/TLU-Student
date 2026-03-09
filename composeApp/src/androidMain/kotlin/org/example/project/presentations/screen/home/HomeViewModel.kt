package org.example.project.presentations.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.model.HomeState
import org.example.project.domain.model.HomeUiEvent
import org.example.project.domain.usecase.ScheduleUseCase
import org.example.project.domain.usecase.StudentUseCase
import org.example.project.presentations.utils.getTodayDayOfWeek

class HomeViewModel(
    private val studentUseCase: StudentUseCase,
    private val scheduleUseCase: ScheduleUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    private val _event = Channel<HomeUiEvent>()
    val event = _event.receiveAsFlow()

    init {
        observeStudentInfo()
        observeCourseClasses()
        loadInitData()
    }

    private fun observeCourseClasses(){
        viewModelScope.launch {
            scheduleUseCase.dayOfWeekSchedule.collect { courseClasses ->
                courseClasses?.let {
                    updateState { copy(courseClasses = it.courseClasses, loadingScheduleClassList = false) }
                }
            }
        }
    }

    private fun observeStudentInfo() {
        viewModelScope.launch {
            studentUseCase.studentInfo.collect { studentInformation ->
                studentInformation?.let {
                    updateState { copy(studentInfo = it, loadingStudentInfo = false) }
                }
            }
        }
    }

    private fun loadInitData() {
        viewModelScope.launch {
            coroutineScope {
                launch { loadStudentInfo() }
                launch { loadCourseClasses() }
            }
        }
    }

    private suspend fun loadCourseClasses(){
        _uiState.update { it.copy(loadingScheduleClassList = true) }
        scheduleUseCase.getDayOfWeekSchedule(getTodayDayOfWeek()).fold(
            onSuccess = {
                _uiState.update { it.copy(loadingScheduleClassList = false) }
            },
            onFailure = {
                _uiState.update { it.copy(loadingScheduleClassList = false) }
            }
        )
    }

    private suspend fun loadStudentInfo() {
        _uiState.update { it.copy(loadingStudentInfo = true) }
        studentUseCase.getStudentInfo().fold(
            onSuccess = {
                // TODO:
                _uiState.update { it.copy(loadingStudentInfo = false) }
            },
            onFailure = {
                // TODO:
                _uiState.update { it.copy(loadingStudentInfo = false) }
            }
        )
    }

    fun onAction(action: HomeAction) {
        when (action) {
            else -> Unit
        }
    }

    private fun updateState(newState: HomeState.() -> HomeState) {
        _uiState.update(newState)
    }

    private fun sendUiEvent(event: HomeUiEvent) {
        _event.trySend(event)
    }
}