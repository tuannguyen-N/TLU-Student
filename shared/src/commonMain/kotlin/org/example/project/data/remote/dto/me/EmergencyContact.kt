package org.example.project.data.remote.dto.me

import kotlinx.serialization.Serializable

@Serializable
data class EmergencyContact(
    val address: String,
    val name: String,
    val phoneNumber: String
)