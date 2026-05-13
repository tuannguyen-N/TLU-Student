package org.example.project.presentations.screen.application

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.remote.dto.application.ApplicationType
import org.example.project.domain.model.SubmitState
import org.example.project.domain.repository.ApplicationRepository

class ApplicationViewModel(
    private val applicationRepository: ApplicationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ApplicationState())
    val uiState: StateFlow<ApplicationState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            delay(100L)
            applicationRepository.getApplicationTypes().onSuccess { applicationTypes ->
                _uiState.update { it.copy(applicationTypes = applicationTypes) }
            }

            applicationRepository.getApplicationHistory().onSuccess { applicationHistory ->
                _uiState.update { it.copy(applicationHistory = applicationHistory) }
            }.onFailure {
                Log.e("check_history", "loadData: ${it.message}")
            }
        }
    }

    fun onApplicationChange(applicationType: ApplicationType) {
        _uiState.update {
            it.copy(
                selectedApplicationType = applicationType,
                subjectExpanded = false
            )
        }
    }

    fun onSubjectExpandedChange(expanded: Boolean) {
        _uiState.update { it.copy(subjectExpanded = expanded) }
    }

    fun onAddFile(uri: Uri) {
        _uiState.update { it.copy(attachedFile = uri.toString()) }
    }

    fun onRemoveFile() {
        _uiState.update { it.copy(attachedFile = null) }
    }

    fun onDismiss() {
        _uiState.update { it.copy(submitState = SubmitState.Idle) }
    }

    fun onSubmit(fileBytes: ByteArray) {
        val state = _uiState.value
        if (!state.isFormValid) return

        viewModelScope.launch {
            _uiState.update { it.copy(submitState = SubmitState.Loading) }

            val uri = state.attachedFile!!.toUri()
            val fileName = uri.lastPathSegment ?: "file.pdf"
            val files = listOf(fileName to fileBytes)

            applicationRepository.submitApplication(
                content = state.content,
                files = files,
                applicationType = state.selectedApplicationType!!.id,
            ).onSuccess {
                _uiState.update { it.copy(submitState = SubmitState.Success) }
            }.onFailure { result ->
                _uiState.update { it.copy(submitState = SubmitState.Error(message = result.message!!)) }
            }
        }
    }

    fun refreshData() {
        loadData()
    }

    fun onTabSelected(index: Int) {
        _uiState.update { it.copy(selectedTab = index) }
    }

    fun onContentChange(content: String) {
        _uiState.update { it.copy(content = content) }
    }
}