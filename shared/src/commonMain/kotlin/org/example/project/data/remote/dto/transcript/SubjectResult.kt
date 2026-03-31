package org.example.project.data.remote.dto.transcript

import kotlinx.serialization.Serializable

@Serializable
data class SubjectResult(
    val subjectCode: String,
    val subjectName: String,
    val credits: Int,
    val attendanceScore: Double,
    val midtermScore: Double,
    val finalScore: Double,
    val score10: Double,
    val score4: Double,
    val letterGrade: String,
    val isPass: Boolean
)