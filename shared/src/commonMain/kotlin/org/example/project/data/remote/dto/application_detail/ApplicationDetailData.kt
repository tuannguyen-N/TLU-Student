package org.example.project.data.remote.dto.application_detail

import kotlinx.serialization.Serializable

@Serializable
data class ApplicationDetailData(
    val typeName: String,
    val status: String,
    val content: String?,
    val attachments: List<ApplicationAttachment>? = null,
    val createdAt: String
)
