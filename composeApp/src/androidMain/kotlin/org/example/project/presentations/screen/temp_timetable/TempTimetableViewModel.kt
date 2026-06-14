package org.example.project.presentations.screen.temp_timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.remote.dto.enroll.EnrollmentScheduleData
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.data.remote.dto.week_schedule.DailySchedule
import org.example.project.data.remote.dto.week_schedule.WeeklyScheduleData
import org.example.project.domain.repository.EnrollmentRepository
import org.example.project.presentations.screen.timetable.TimetableState

class TempTimetableViewModel(
    private val enrollmentRepository: EnrollmentRepository,
    private val semesterId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(TimetableState())
    val uiState = _uiState.asStateFlow()

    init {
        observeEnrolledClasses()
        loadEnrollmentSchedule()
    }

    private fun observeEnrolledClasses() {
        enrollmentRepository.enrolledClasses
            .onEach { enrolledList ->
                val weeklySchedule = enrolledList.toWeeklyScheduleData()
                _uiState.value = _uiState.value.copy(weekSchedule = weeklySchedule)
            }
            .launchIn(viewModelScope)
    }

    private fun loadEnrollmentSchedule() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            enrollmentRepository.getEnrollmentSchedule(semesterId)
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun onOpenDetailCourseClass(courseClass: CourseClass) {
        _uiState.value = _uiState.value.copy(
            showDetailCourseClass = true,
            selectedCourseClass = courseClass
        )
    }

    fun onDismissDetailCourseClass() {
        _uiState.value = _uiState.value.copy(showDetailCourseClass = false)
    }

    fun onOpenDetailLecturerInfo() {
        _uiState.value = _uiState.value.copy(showDetailLecturerInfo = true)
    }

    fun onDismissDetailLecturerInfo() {
        _uiState.value = _uiState.value.copy(showDetailLecturerInfo = false)
    }

    private fun List<EnrollmentScheduleData>.toWeeklyScheduleData(): WeeklyScheduleData {
        val dailySchedules = this
            .groupBy { it.dayOfWeek }
            .entries
            .sortedBy { it.key }
            .map { (_, enrollments) ->
                DailySchedule(
                    courseClasses = enrollments.map { enroll ->
                        CourseClass(
                            classCode = enroll.classCode,
                            dayOfWeek = enroll.dayOfWeek,
                            subjectName = enroll.subjectName,
                            subjectCode = enroll.subjectCode,
                            credits = enroll.credits,
                            startPeriod = enroll.startPeriod,
                            endPeriod = enroll.endPeriod,
                            startTime = enroll.startTime,
                            endTime = enroll.endTime,
                            room = enroll.room,
                            lecturer = enroll.lecturer
                        )
                    }
                )
            }

        return WeeklyScheduleData(
            semester = "",
            week = 0,
            startDate = "",
            endDate = "",
            dailySchedules = dailySchedules
        )
    }
}
