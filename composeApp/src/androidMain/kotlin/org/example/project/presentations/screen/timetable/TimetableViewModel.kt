package org.example.project.presentations.screen.timetable

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.domain.model.TimetableUiState
import org.example.project.domain.usecase.ScheduleUseCase
import org.example.project.presentations.utils.getCurrentWeek
import org.example.project.presentations.utils.getNextWeek
import org.example.project.presentations.utils.getPreviousWeek
import org.example.project.presentations.utils.today

class TimetableViewModel(
    private val scheduleUseCase: ScheduleUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TimetableUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<TimetableUIEvent>()
    val events = _events.receiveAsFlow()

    init {
        observeWeekSchedule()
        loadData()
    }

    private fun loadData() {
        val (startTime, endTime) = getCurrentWeek()
        getWeekSchedule(startTime, endTime)
    }

    private fun observeWeekSchedule() {
        scheduleUseCase.weekSchedule.onEach {
            it?.let { data ->
                updateState { copy(weekSchedule = data) }
            }
        }
            .launchIn(viewModelScope)
    }

    private fun getWeekSchedule(startTime: String, endTime: String) {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            scheduleUseCase.getWeekSchedule(startTime, endTime).fold(
                onSuccess = {
                    updateState { copy(isLoading = false) }
                },
                onFailure = {
                    Log.e("TimetableViewModel", "getWeekSchedule error: $it")
                    updateState { copy(isLoading = false) }
                }
            )
        }
    }

    fun onGetNextWeekSchedule() {
        val currentDate = uiState.value.weekSchedule?.startDate
            ?.let { LocalDate.parse(it) } ?: today
        val (startTime, endTime) = getNextWeek(currentDate)
        getWeekSchedule(startTime, endTime)
    }

    fun onGetPreviousWeekSchedule() {
       val currentDate = uiState.value.weekSchedule?.startDate
           ?.let { LocalDate.parse(it) }?: today
        val (startTime, endTime) = getPreviousWeek(currentDate)
        getWeekSchedule(startTime, endTime)
    }

    fun onOpenDetailCourseClass(courseClass: CourseClass) {
        updateState { copy(showDetailCourseClass = true, selectedCourseClass = courseClass) }
    }

    fun onDismissDetailCourseClass() {
        updateState { copy(showDetailCourseClass = false) }
    }

    fun onOpenDetailLecturerInfo() {
        updateState { copy(showDetailLecturerInfo = true) }
    }

    fun onDismissDetailLecturerInfo() {
        updateState { copy(showDetailLecturerInfo = false) }
    }

    private fun updateState(newState: TimetableUiState.() -> TimetableUiState) {
        _uiState.value = _uiState.value.newState()
    }
}