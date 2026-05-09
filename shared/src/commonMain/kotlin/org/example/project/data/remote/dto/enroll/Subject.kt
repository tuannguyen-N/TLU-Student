package org.example.project.data.remote.dto.enroll

import kotlinx.serialization.Serializable

@Serializable
data class Subject(
    val coefficient: Double,
    val credits: Int,
    val departmentCode: String?,
    val departmentName: String?,
    val electiveGroup: String?,
    val facultyCode: String,
    val facultyName: String,
    val id: Int,
    val isRequired: Boolean,
    val lectureHours: Int,
    val practiceHours: Int?,
    val subjectCode: String,
    val subjectName: String
)