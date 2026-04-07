package org.example.project.presentations.screen.timetable_offline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.example.project.data.mapper.getCurrentWeek
import org.example.project.data.mapper.getNextWeek
import org.example.project.data.mapper.getPreviousWeek
import org.example.project.data.mapper.toLocalDateSafe
import org.example.project.data.mapper.toStartWeekDate
import org.example.project.data.mapper.today
import org.example.project.data.remote.dto.semester.Semester
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.data.remote.dto.week_schedule.WeeklyScheduleData
import org.example.project.domain.usecase.ScheduleUseCase
import org.example.project.domain.usecase.SemesterUseCase
import org.example.project.presentations.screen.timetable.TimetableState
import org.example.project.presentations.utils.withDelayedLoading

class OfflineTimetableViewModel(
    private val scheduleUseCase: ScheduleUseCase,
    private val semesterUseCase: SemesterUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TimetableState())
    val uiState = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun fetchSemesters() {
        viewModelScope.launch {
            withDelayedLoading(onLoading = { updateState { copy(isLoading = it) } }) {
                semesterUseCase.getSemesters(true).fold(
                    onSuccess = { result ->
                        val semesters = result as? List<Semester>
                        val selected = semesters?.lastOrNull()
                        updateState {
                            copy(
                                semesters = semesters ?: emptyList(),
                                selectedSemester = selected
                            )
                        }
                    },
                    onFailure = {
                        updateState { copy(semesters = emptyList()) }
                    }
                )
            }
        }
    }

    private fun fetchWeekScheduleOffline(startTime: String, endTime: String) {
        viewModelScope.launch {
            updateState { copy(selectedWeek = "$startTime - $endTime") }
            withDelayedLoading(onLoading = { updateState { copy(isLoading = it) } }) {
                scheduleUseCase.getWeekSchedule(startTime, endTime, true).fold(
                    onSuccess = { result ->
                        val data = result as? WeeklyScheduleData?
                        data?.let {
                            updateState { copy(weekSchedule = it) }
                        }
                    },
                    onFailure = {
                        updateState { copy(weekSchedule = null) }
                    }
                )
            }
        }
    }

    private fun loadData() {
        val (startTime, endTime) = getCurrentWeek()
        fetchSemesters()
        fetchWeekScheduleOffline(startTime, endTime)
    }

    fun onGetNextWeekSchedule() {
        val currentDate = uiState.value.weekSchedule?.startDate
            ?.let { LocalDate.parse(it) } ?: today
        val (startTime, endTime) = getNextWeek(currentDate)
        fetchWeekScheduleOffline(startTime, endTime)
    }

    fun onGetPreviousWeekSchedule() {
        val currentDate = uiState.value.weekSchedule?.startDate
            ?.let { LocalDate.parse(it) } ?: today
        val (startTime, endTime) = getPreviousWeek(currentDate)
        fetchWeekScheduleOffline(startTime, endTime)
    }

    fun onOpenDetailCourseClass(courseClass: CourseClass) {
        updateState {
            copy(
                showDetailCourseClass = true,
                selectedCourseClass = courseClass
            )
        }
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
        updateState { copy(selectedSemester = semester) }
        fetchWeekScheduleOffline(start, end)
    }

    fun onChangeWeek(week: String) {
        val (start, end) = week.split(" - ")
        fetchWeekScheduleOffline(start, end)
        updateState { copy(selectedWeek = week) }
    }

    fun onToggleDropDown() {
        updateState { copy(showWeekMenu = !showWeekMenu) }
    }

    private fun updateState(newState: TimetableState.() -> TimetableState) {
        _uiState.value = _uiState.value.newState()
    }
}
