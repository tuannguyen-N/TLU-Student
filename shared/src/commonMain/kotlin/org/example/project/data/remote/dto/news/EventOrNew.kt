package org.example.project.data.remote.dto.news

import kotlinx.serialization.Serializable

@Serializable
data class EventOrNew(
    val excerpt: String,
    val imageUrl: String,
    val newsUrl: String,
    val publishDate: String,
    val source: String,
    val title: String
)