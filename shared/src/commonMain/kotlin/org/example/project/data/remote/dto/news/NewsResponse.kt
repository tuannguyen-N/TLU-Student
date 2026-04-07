package org.example.project.data.remote.dto.news

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val code: Int,
    val data: NewsContent?,
    val message: String
)