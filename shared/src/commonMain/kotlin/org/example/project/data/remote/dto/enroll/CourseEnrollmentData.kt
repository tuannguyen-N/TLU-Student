package org.example.project.data.remote.dto.enroll

import kotlinx.serialization.Serializable

@Serializable
data class CourseEnrollmentData(
    val semesterId: Int,
    val studyProgramCode: String,
    val studyProgramId: Int,
    val studyProgramName: String,
    val subjects: List<Subject>
)