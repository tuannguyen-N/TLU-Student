package org.example.project.data.remote.dto.me

import kotlinx.serialization.Serializable

@Serializable
data class EmergencyContact(
    val name: String,
    val phoneNumber: String,
    val address: String,
    val relationship: String?
)