package org.example.project.data.remote.dto.feedback

import kotlinx.serialization.Serializable

@Serializable
data class FeedbackCategoryResponse(
    val code: Int,
    val data: List<FeedbackCategoryData>?,
    val message: String
)