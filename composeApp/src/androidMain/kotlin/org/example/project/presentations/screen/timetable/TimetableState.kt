package org.example.project.presentations.screen.timetable

import org.example.project.data.remote.dto.semester.Semester
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.data.remote.dto.week_schedule.WeeklyScheduleData

data class TimetableState(
    val weekSchedule: WeeklyScheduleData? = null,
    val semesters: List<Semester> = emptyList(),
    val selectedSemester: Semester? = null,
    val selectedCourseClass: CourseClass? = null,
    val selectedWeek: String = "",

    val showWeekMenu: Boolean = false,

    val showDetailCourseClass: Boolean = false,
    val showDetailLecturerInfo: Boolean = false,
    val isLoading: Boolean = false
)