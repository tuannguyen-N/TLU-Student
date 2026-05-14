package org.example.project.data.remote.dto.feedback

import kotlinx.serialization.Serializable

@Serializable
data class FeedbackCategoryData(
    val description: String,
    val id: Int,
    val name: String
)