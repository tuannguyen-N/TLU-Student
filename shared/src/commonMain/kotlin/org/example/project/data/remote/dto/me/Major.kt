package org.example.project.data.remote.dto.me

import kotlinx.serialization.Serializable

@Serializable
data class Major(
    val faculty: String,
    val majorCode: String,
    val majorName: String
)