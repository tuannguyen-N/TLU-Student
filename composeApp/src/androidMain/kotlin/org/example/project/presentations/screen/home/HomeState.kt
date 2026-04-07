package org.example.project.presentations.screen.home

import org.example.project.data.remote.dto.me.StudentData
import org.example.project.data.remote.dto.news.EventOrNew
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.domain.model.AlertUiModel
import org.example.project.domain.model.EventAndNewUiModel
import org.example.project.domain.model.FeatureUiModel

data class HomeState(
    val studentInfo: StudentData? = null,
    val courseClasses: List<CourseClass>? = null,
    val quickAccessList: List<FeatureUiModel> = emptyList(),
    val alerts: List<AlertUiModel> = AlertUiModel.getDemoList(),
    val newsAndEvents: List<EventAndNewUiModel> = emptyList(),
    val imageBase64: String? = null,

    val loadingStudentInfo: Boolean = false,
    val loadingAlertList: Boolean = false,
    val loadingScheduleClassList: Boolean = false,
    val loadingEventList: Boolean = false,
    val isRefreshing: Boolean = false
) 