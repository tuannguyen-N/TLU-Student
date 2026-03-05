package org.example.project.data.remote.dto.login

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val accessToken: String
)