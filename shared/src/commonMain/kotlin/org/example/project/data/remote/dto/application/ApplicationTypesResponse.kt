package org.example.project.data.remote.dto.application

import kotlinx.serialization.Serializable

@Serializable
data class ApplicationTypesResponse(
    val code: Int,
    val data: List<ApplicationType>?,
    val message: String
)