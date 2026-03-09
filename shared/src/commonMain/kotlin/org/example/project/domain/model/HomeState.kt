package org.example.project.domain.model

import org.example.project.data.remote.dto.me.StudentInformation
import org.example.project.data.remote.dto.schedule.CourseClass

data class HomeState(
    val studentInfo: StudentInformation? = null,
    val courseClasses: List<CourseClass> ? = null,
    val quickAccessList: List<FeatureUiModel> = emptyList(),

    val loadingStudentInfo: Boolean = false,
    val loadingAlertList: Boolean = false,
    val loadingScheduleClassList: Boolean = false,
    val loadingEventList: Boolean = false
)