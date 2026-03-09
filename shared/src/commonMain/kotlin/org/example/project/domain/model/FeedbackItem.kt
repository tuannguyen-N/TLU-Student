package org.example.project.domain.model

data class FeedbackItem(
    val id: String,
    val title: String,
    val preview: String,
    val date: String,
    val status: FeedbackStatus
)