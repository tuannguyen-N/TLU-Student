package org.example.project.data.remote.dto.enroll

import kotlinx.serialization.Serializable

@Serializable
data class ConflictScheduleData(
    val dayOfWeek: Int,
    val startPeriod: Int,
    val endPeriod: Int,
    val classOverlapCode: String
)