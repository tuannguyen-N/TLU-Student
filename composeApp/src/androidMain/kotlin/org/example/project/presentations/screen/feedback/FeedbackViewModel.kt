package org.example.project.presentations.screen.feedback

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.model.FeedBackState
import org.example.project.domain.model.SubjectOption

class FeedbackViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(FeedBackState())
    val uiState: StateFlow<FeedBackState> = _uiState.asStateFlow()

    fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTab = index) }
    }

    fun onTitleChange(value: String) {
        _uiState.update { it.copy(title = value) }
    }

    fun onContentChange(value: String) {
        _uiState.update { it.copy(content = value) }
    }

    fun onSubjectChange(value: SubjectOption) {
        _uiState.update { it.copy(subject = value) }
    }

    fun onAddImage(image: Uri){
        _uiState.update { it.copy(attachedImages = it.attachedImages + image.toString()) }
    }

    fun onRemoveImage(image: Uri){
        _uiState.update { it.copy(attachedImages = it.attachedImages - image.toString()) }
    }

    fun onSubjectExpandedChange(expanded: Boolean) {
        _uiState.update { it.copy(subjectExpanded = expanded) }
    }

    fun onSubmit() {
        if (!_uiState.value.isFormValid) return
        viewModelScope.launch {

        }
    }
}