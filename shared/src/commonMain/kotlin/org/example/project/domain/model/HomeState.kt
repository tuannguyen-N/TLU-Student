package org.example.project.domain.model

import org.example.project.data.remote.dto.me.StudentInformation

data class HomeState(
    val studentInfo: StudentInformation? = null,
    val loadingAlertList: Boolean = false,
    val loadingScheduleClassList: Boolean = false,
    val loadingEventList: Boolean = false
)