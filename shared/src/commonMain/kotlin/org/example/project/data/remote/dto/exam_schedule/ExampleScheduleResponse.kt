package org.example.project.data.remote.dto.exam_schedule

import kotlinx.serialization.Serializable

@Serializable
data class ExampleScheduleResponse(
    val code: Int,
    val examScheduleData: ExamScheduleData?,
    val message: String
)