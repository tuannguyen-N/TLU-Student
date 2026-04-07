package org.example.project.domain.model

data class EventAndNewUiModel(
    val excerpt: String,
    val imageUrl: String?,
    val newsUrl: String,
    val publishDate: String,
    val source: String,
    val title: String,
    val isNew: Boolean,
    val timeAgo: String
)
