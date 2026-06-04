package org.example.project.data.remote.dto.upload_image

import kotlinx.serialization.Serializable

@Serializable
data class UploadImageResponse(
    val code: Int,
    val data: UploadImageData,
    val message: String
)