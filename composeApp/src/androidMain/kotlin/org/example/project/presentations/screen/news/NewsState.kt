package org.example.project.presentations.screen.news

import org.example.project.domain.model.EventAndNewUiModel

data class NewsState(
    val news: List<EventAndNewUiModel> = emptyList(),
    val isLoading: Boolean = false
)