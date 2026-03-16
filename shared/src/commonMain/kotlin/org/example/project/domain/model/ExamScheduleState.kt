package org.example.project.domain.model

import kotlinx.datetime.LocalDate
import org.example.project.data.remote.dto.semester.Semester

data class ExamScheduleState(
    val semesters: List<Semester> = emptyList(),
    val selectedSemester: Semester? = null,
    val currentSemester: Semester? = null,

    val examDays: List<ExamDay> = emptyList(),
    val examDay: ExamDay? = null,
    val selectedDate: LocalDate,

    val selectedTab: Int = 0,
    val isDropdownExpanded: Boolean = false,

    val isLoading: Boolean = false,
    val error: String? = null
)