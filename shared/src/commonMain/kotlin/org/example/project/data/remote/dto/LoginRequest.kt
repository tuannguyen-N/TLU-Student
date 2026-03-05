package org.example.project.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val accessToken: String
)