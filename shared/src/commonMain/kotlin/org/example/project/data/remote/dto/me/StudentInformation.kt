package org.example.project.data.remote.dto.me

import kotlinx.serialization.Serializable

@Serializable
data class StudentInformation(
    val academicAdvisor: String,
    val academicInfo: AcademicInfo,
    val classCode: String,
    val contact: Contact,
    val dateOfBirth: String,
    val emergencyContact: EmergencyContact,
    val fullName: String,
    val gender: String,
    val identityCard: IdentityCard,
    val major: Major,
    val studentCode: String
)