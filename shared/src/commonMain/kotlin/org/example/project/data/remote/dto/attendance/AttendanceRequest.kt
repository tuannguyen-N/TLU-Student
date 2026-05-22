package org.example.project.data.remote.dto.attendance

import kotlinx.serialization.Serializable

@Serializable
data class AttendanceRequest(
    val qrToken: String,
    val latitude: Double,
    val longitude: Double
)
