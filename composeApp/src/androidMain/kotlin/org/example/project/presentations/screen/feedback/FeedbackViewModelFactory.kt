package org.example.project.presentations.screen.feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.repository.FeedbackRepository

class FeedbackViewModelFactory(
    private val feedbackRepository: FeedbackRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(FeedbackViewModel::class.java)) {
            return FeedbackViewModel(feedbackRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}