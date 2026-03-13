package org.example.project.data.remote.dto.exam_schedule

import kotlinx.serialization.Serializable

@Serializable
data class ExamScheduleData(
    val examSchedules: List<ExamSchedule>,
    val semesterName: String
)