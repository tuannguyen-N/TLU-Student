package org.example.project.data.remote.dto.upload_image

import kotlinx.serialization.Serializable

@Serializable
data class UploadImageData(
    val url: String
)