package org.example.project.data.remote.dto.me

import kotlinx.serialization.Serializable

@Serializable
data class AcademicInfo(
    val cohort: String,
    val educationMode: String,
    val position: String
)