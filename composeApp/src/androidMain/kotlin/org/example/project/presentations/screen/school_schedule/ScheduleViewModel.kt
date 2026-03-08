package org.example.project.presentations.screen.school_schedule

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.remote.dto.schedule.CourseClass
import org.example.project.domain.model.ScheduleState
import org.example.project.domain.usecase.ScheduleUseCase
import org.example.project.presentations.utils.getTodayDayOfWeek

class ScheduleViewModel(
    private val scheduleUseCase: ScheduleUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ScheduleState(selectedDayOfWeek = getTodayDayOfWeek()))
    val uiState = _uiState.asStateFlow()

    init {
        observeDayOfWeekSchedule()
        loadData()
    }

    private fun observeDayOfWeekSchedule() {
        viewModelScope.launch {
            scheduleUseCase.dayOfWeekSchedule.collect { dayOfWeekSchedule ->
                dayOfWeekSchedule?.let {
                    updateState { copy(courseClasses = it.data?.courseClasses) }
                }
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            scheduleUseCase.getDayOfWeekSchedule(getTodayDayOfWeek()).fold(
                onSuccess = {
                    updateState { copy(isLoading = false) }
                },
                onFailure = {
                    Log.e("123123", "loadData: $it", )
                    updateState { copy(isLoading = false) }
                }
            )
        }
    }

    fun onChangeDayOfWeek(value: Int) {
        viewModelScope.launch {
            updateState { copy(selectedDayOfWeek = value) }
            scheduleUseCase.getDayOfWeekSchedule(value)
        }
    }

    fun onOpenDetailCourseClass(courseClass: CourseClass) {
        updateState { copy(showDetailCourseClass = true, selectedCourseClass = courseClass) }
    }

    fun onDismissDetailCourseClass() {
        updateState { copy(showDetailCourseClass = false) }
    }

    private fun updateState(newState: ScheduleState.() -> ScheduleState) {
        _uiState.value = _uiState.value.newState()
    }
}