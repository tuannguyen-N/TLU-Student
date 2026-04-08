package org.example.project.presentations.screen.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.repository.NewsRepository
import org.example.project.presentations.utils.withDelayedLoading

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewsState())
    val uiState = _uiState.asStateFlow()

    init {
        loadNews()
    }

    private fun loadNews() {
        viewModelScope.launch {
            withDelayedLoading(delayMs = 0L, onLoading = { updateState { copy(isLoading = it) } }) {
                delay(400L)
                newsRepository.getNews().onSuccess { news ->
                    updateState { copy(news = news) }
                }
            }
        }
    }

    private fun updateState(newState: NewsState.() -> NewsState) {
        _uiState.update(newState)
    }
}