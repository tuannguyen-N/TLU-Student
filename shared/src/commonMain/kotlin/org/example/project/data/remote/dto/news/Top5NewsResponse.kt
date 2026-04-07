package org.example.project.data.remote.dto.news

import kotlinx.serialization.Serializable

@Serializable
data class Top5NewsResponse(
    val code: Int,
    val data: List<EventOrNew>?,
    val message: String
)