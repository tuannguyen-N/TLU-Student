package org.example.project.data.remote.dto.me

import kotlinx.serialization.Serializable

@Serializable
data class StudentData(
    val studentCode: String,
    val fullName: String,
    val dateOfBirth: String,
    val gender: String,
    val classCode: String,
    val academicAdvisor: String,
    val startYear: Int,
    val endYear: Int,
    val major: Major,
    val trainingType: String,
    val identityCard: IdentityCard,
    val contact: Contact,
    val academicInfo: AcademicInfo,
    val emergencyContact: EmergencyContact
)
