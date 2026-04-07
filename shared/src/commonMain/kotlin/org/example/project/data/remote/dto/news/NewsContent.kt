package org.example.project.data.remote.dto.news

import kotlinx.serialization.Serializable

@Serializable
data class NewsContent(
    val content: List<EventOrNew>,
    val first: Boolean,
    val last: Boolean,
    val page: Int,
    val size: Int,
    val totalElements: Int,
    val totalPages: Int
)