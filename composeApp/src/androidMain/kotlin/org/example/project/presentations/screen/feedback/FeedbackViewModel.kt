package org.example.project.presentations.screen.feedback

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.remote.dto.feedback.FeedbackCategoryData
import org.example.project.domain.model.AppResult
import org.example.project.domain.model.SubmitState
import org.example.project.domain.repository.FeedbackRepository

class FeedbackViewModel(
    private val repository: FeedbackRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(FeedBackState())
    val uiState: StateFlow<FeedBackState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            repository.getCategory().onSuccess {categories->
                _uiState.update { it.copy(feedbackCategories = categories) }
            }.onFailure {
                Log.e("check_feedback", "loadCategories: ${it.message}", )
            }
        }
    }

    fun onTitleChange(value: String) {
        _uiState.update { it.copy(title = value) }
    }

    fun onContentChange(value: String) {
        _uiState.update { it.copy(content = value) }
    }

    fun onSubjectChange(value: FeedbackCategoryData) {
        _uiState.update { it.copy(feedbackCategory = value) }
    }

    fun onAddImage(image: Uri) {
        _uiState.update { it.copy(attachedImages = it.attachedImages + image.toString()) }
    }

    fun onRemoveImage(image: Uri) {
        _uiState.update { it.copy(attachedImages = it.attachedImages - image.toString()) }
    }

    fun onSubjectExpandedChange(expanded: Boolean) {
        _uiState.update { it.copy(subjectExpanded = expanded) }
    }

    fun onSubmit(files: List<Pair<String, ByteArray>>) {
        if (!_uiState.value.isFormValid) return
        viewModelScope.launch {
            _uiState.update { it.copy(submitState = SubmitState.Loading) }
            val state = _uiState.value

            val title = state.title
            val content = state.content
            val categoryId = state.feedbackCategory?.id?.toLong() ?: 0L
            when (val result = repository.sendFeedback(files, title, categoryId, content)) {
                is AppResult.Success -> {
                    _uiState.update { it.copy(submitState = SubmitState.Success) }
                }
                is AppResult.Failure -> {
                    _uiState.update { it.copy(submitState = SubmitState.Error(message = result.message ?: "")) }
                }
            }
        }
    }

    fun onDismiss() {
        _uiState.update { it.copy(submitState = SubmitState.Idle) }
    }
}