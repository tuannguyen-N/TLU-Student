package org.example.project.data.remote.dto.transcript

import kotlinx.serialization.Serializable

@Serializable
data class SubjectResult(
    val credits: Int,
    val isPass: Boolean,
    val letterGrade: String,
    val score10: Double,
    val score4: Double,
    val subjectCode: String,
    val subjectName: String
)