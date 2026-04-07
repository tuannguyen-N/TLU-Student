package org.example.project.presentations.screen.exam_schedule

import kotlinx.datetime.LocalDate
import org.example.project.data.mapper.today
import org.example.project.data.remote.dto.semester.Semester
import org.example.project.domain.model.ExamDay

data class ExamScheduleState(
    val semesters: List<Semester> = emptyList(),
    val selectedSemester: Semester? = null,
    val currentSemester: Semester? = null,

    val examDays: List<ExamDay> = emptyList(),
    val examDay: ExamDay? = null,
    val selectedDate: LocalDate = today,
    val resetTrigger: Int = 0,

    val selectedTab: Int = 0,
    val isDropdownExpanded: Boolean = false,

    val isLoading: Boolean = false,
    val error: String? = null
)