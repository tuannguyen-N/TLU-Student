package org.example.project.data.remote.dto.application

import kotlinx.serialization.Serializable

@Serializable
data class ApplicationType(
    val code: String,
    val id: Int,
    val isActive: Boolean,
    val name: String
)