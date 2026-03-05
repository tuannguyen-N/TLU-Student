package org.example.project.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginDataResponse(
    val microsoftId: String,
    val email: String,
    val name: String,
    val token: String
)
