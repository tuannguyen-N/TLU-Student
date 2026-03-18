package org.example.project.data.remote.dto.exam_schedule

import kotlinx.serialization.Serializable

@Serializable
data class ExampleScheduleResponse(
    val code: Int,
    val data: ExamScheduleData?,
    val message: String
)