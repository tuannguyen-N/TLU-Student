package org.example.project.presentations.screen.school_schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.example.project.data.mapper.getTodayDayOfWeek
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.domain.repository.NotificationRepository
import org.example.project.domain.usecase.ScheduleUseCase

class ScheduleViewModel(
    private val scheduleUseCase: ScheduleUseCase,
    private val notificationRepository: NotificationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        ScheduleState(
            selectedDayOfWeek = getTodayDayOfWeek(), currentDay = getTodayDayOfWeek()
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        observeDayOfWeekSchedule()
        observeReadNotifications()
        loadData()
    }

    private fun observeReadNotifications() {
        notificationRepository.hasUnreadNotifications.onEach { hasUnread ->
            updateState { copy(isAllNotificationsRead = !hasUnread) }
        }.launchIn(viewModelScope)
    }

    private fun observeDayOfWeekSchedule() {
        combine(
            scheduleUseCase.daySchedule,
            _uiState.map { it.selectedDayOfWeek }.distinctUntilChanged()
        ) { cache, selectedDay ->
            cache[selectedDay]
        }.onEach { data ->
                data?.let {
                    updateState { copy(courseClasses = it.courseClasses) }
                }
            }.launchIn(viewModelScope)
    }

    private fun loadData() {
        viewModelScope.launch {
//            updateState { copy(isLoading = true) }
//            scheduleUseCase.getDayOfWeekSchedule(getTodayDayOfWeek()).fold(onSuccess = {
//                updateState { copy(isLoading = false) }
//            }, onFailure = {
//                Log.e("123123", "loadData: $it")
//                updateState { copy(isLoading = false) }
//            })
        }
    }

    fun onClickViewTomorrow() {
        if (uiState.value.selectedDayOfWeek == 7) onChangeDayOfWeek(1)
        else onChangeDayOfWeek(uiState.value.selectedDayOfWeek + 1)
    }

    fun onChangeDayOfWeek(value: Int) {
        viewModelScope.launch {
            updateState { copy(selectedDayOfWeek = value) }
            scheduleUseCase.getDaySchedule(value)
        }
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

    private fun updateState(newState: ScheduleState.() -> ScheduleState) {
        _uiState.value = _uiState.value.newState()
    }
}