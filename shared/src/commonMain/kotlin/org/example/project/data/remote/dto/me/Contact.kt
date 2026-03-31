package org.example.project.data.remote.dto.me

import kotlinx.serialization.Serializable

@Serializable
data class Contact(
    val phoneNumber: String,
    val address: String,
    val email: String
)