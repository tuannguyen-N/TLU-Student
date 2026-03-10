package org.example.project.domain.model

import org.example.project.data.remote.dto.me.StudentInformation
import org.example.project.data.remote.dto.day_schedule.CourseClass

data class HomeState(
    val studentInfo: StudentInformation? = null,
    val courseClasses: List<CourseClass> ? = null,
    val quickAccessList: List<FeatureUiModel> = emptyList(),
    val alerts: List<AlertUiModel> = AlertUiModel.getDemoList(),
    val newsAndEvents: List<NewAndEventUiModel> = NewAndEventUiModel.getDataDemo(),

    val loadingStudentInfo: Boolean = false,
    val loadingAlertList: Boolean = false,
    val loadingScheduleClassList: Boolean = false,
    val loadingEventList: Boolean = false
)