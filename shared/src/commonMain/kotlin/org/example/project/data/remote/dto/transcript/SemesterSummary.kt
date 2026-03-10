package org.example.project.data.remote.dto.transcript

import kotlinx.serialization.Serializable

@Serializable
data class SemesterSummary(
    val creditsRegistered: Int?,
    val creditsPassed: Int,
    val semesterGpa: Double,
    val conductScore: Int,
    val cumulativeGpa: Double
)