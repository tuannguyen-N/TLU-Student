package org.example.project.presentations.screen.timetable

import android.util.Log
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
import kotlinx.datetime.LocalDate
import org.example.project.data.mapper.toLocalDateSafe
import org.example.project.data.mapper.toStartWeekDate
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.domain.usecase.ScheduleUseCase
import org.example.project.domain.usecase.SemesterUseCase
import org.example.project.data.mapper.getCurrentWeek
import org.example.project.data.mapper.getNextWeek
import org.example.project.data.mapper.getPreviousWeek
import org.example.project.data.mapper.today
import org.example.project.presentations.utils.withDelayedLoading

class TimetableViewModel(
    private val scheduleUseCase: ScheduleUseCase,
    private val semesterUseCase: SemesterUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TimetableState())
    val uiState = _uiState.asStateFlow()

    init {
        observeWeekSchedule()
        observeSemesters()
        loadData()
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

    private fun observeWeekSchedule() {
        combine(
            scheduleUseCase.weekSchedule,
            _uiState.map { it.selectedWeek }.distinctUntilChanged()
        ) { cache, selectedWeek ->
            cache[selectedWeek]
        }
            .onEach { data ->
                data?.let {
                    Log.e("TimetableViewModel", "observeWeekSchedule: $it")
                    updateState { copy(weekSchedule = it) }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadData() {
        val (startTime, endTime) = getCurrentWeek()
        getSemesters()
        getWeekSchedule(startTime, endTime)
    }

    private fun getSemesters() {
        viewModelScope.launch {
            semesterUseCase.getSemesters()
        }
    }

    private fun getWeekSchedule(startTime: String, endTime: String) {
        viewModelScope.launch {
            updateState { copy(selectedWeek = "$startTime - $endTime") }

            withDelayedLoading(
                onLoading = { isLoading ->
                    updateState { copy(isLoading = isLoading) }
                }
            ) {
                scheduleUseCase.getWeekSchedule(startTime, endTime)
            }
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
            ?.let { LocalDate.parse(it) } ?: today
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

    fun onChangeSemester(semesterName: String) {
        val semester = _uiState.value.semesters.find { it.semesterName == semesterName } ?: return
        val startDate = semester.startDate.toLocalDateSafe()
        val endDate = semester.endDate.toLocalDateSafe()

        val (start, end) = if (today in startDate..endDate) {
            getCurrentWeek()
        } else {
            val firstWeek = semester.toStartWeekDate()
            val (s, e) = firstWeek.split(" - ")
            s to e
        }
        getWeekSchedule(start, end)
        updateState {
            copy(
                selectedSemester = semester,
                selectedWeek = "$start - $end"
            )
        }
    }

    fun onChangeWeek(week: String) {
        val (start, end) = week.split(" - ")
        getWeekSchedule(start, end)
        updateState { copy(selectedWeek = week) }
    }

    fun onToggleDropDown() {
        updateState { copy(showWeekMenu = !showWeekMenu) }
    }

    private fun updateState(newState: TimetableState.() -> TimetableState) {
        _uiState.value = _uiState.value.newState()
    }
}