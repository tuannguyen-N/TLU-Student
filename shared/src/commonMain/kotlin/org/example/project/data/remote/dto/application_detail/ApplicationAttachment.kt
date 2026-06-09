package org.example.project.data.remote.dto.application_detail

import kotlinx.serialization.Serializable

@Serializable
data class ApplicationAttachment(
    val id: Int,
    val fileKey: String,
    val originalFilename: String,
    val fileSize: Long,
    val resourceType: String?
)