package org.example.project.presentations.screen.home

import org.example.project.data.remote.dto.me.StudentInformation
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.domain.model.AlertUiModel
import org.example.project.domain.model.FeatureUiModel
import org.example.project.domain.model.NewAndEventUiModel

data class HomeState(
    val studentInfo: StudentInformation? = null,
    val courseClasses: List<CourseClass> ? = null,
    val quickAccessList: List<FeatureUiModel> = emptyList(),
    val alerts: List<AlertUiModel> = AlertUiModel.Companion.getDemoList(),
    val newsAndEvents: List<NewAndEventUiModel> = NewAndEventUiModel.Companion.getDataDemo(),

    val loadingStudentInfo: Boolean = false,
    val loadingAlertList: Boolean = false,
    val loadingScheduleClassList: Boolean = false,
    val loadingEventList: Boolean = false
) 