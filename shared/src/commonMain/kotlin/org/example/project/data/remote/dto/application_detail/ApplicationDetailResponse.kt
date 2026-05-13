package org.example.project.data.remote.dto.application_detail

import kotlinx.serialization.Serializable

@Serializable
data class ApplicationDetailResponse(
    val code: Int,
    val message: String,
    val data: ApplicationDetailData? = null
)