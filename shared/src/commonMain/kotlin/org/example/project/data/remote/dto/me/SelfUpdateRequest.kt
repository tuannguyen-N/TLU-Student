package org.example.project.data.remote.dto.me

import kotlinx.serialization.Serializable

@Serializable
data class SelfUpdateRequest(
    val phoneNumber: String? = null,
    val address: String? = null,
    val email: String? = null,
    val emergencyContactName: String? = null,
    val emergencyContactPhoneNumber: String? = null,
    val emergencyContactAddress: String? = null,
    val emergencyContactRelationship: String? = null
)
