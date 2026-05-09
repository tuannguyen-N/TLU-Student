package org.example.project.presentations.screen.transcript

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.example.project.data.mapper.TranscriptMapper
import org.example.project.domain.repository.NotificationRepository
import org.example.project.domain.usecase.TranscriptUseCase
import org.example.project.presentations.utils.withDelayedLoading

class TranscriptViewModel(
    private val transcriptUseCase: TranscriptUseCase,
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TranscriptState())
    val uiState = _uiState.asStateFlow()

    init {
        observeTranscript()
        observeReadNotifications()
        observeStudyProgram()
        loadData()
    }

    private fun observeStudyProgram() {
        transcriptUseCase.studyProgram.onEach { studyProgram ->
            updateState { copy(totalCredits = studyProgram?.totalCredits ?: 136) }
        }.launchIn(viewModelScope)
    }

    private fun observeReadNotifications() {
        notificationRepository.readNotificationIds.onEach { readIds ->
            updateState { copy(isAllNotificationsRead = readIds.isEmpty()) }
        }.launchIn(viewModelScope)
    }

    private fun observeTranscript() {
        transcriptUseCase.transcriptCached
            .filterNotNull()
            .onEach { uiModel ->
                Log.e("123123", "observeTranscript: $uiModel")
                updateState { copy(transcriptUiModel = uiModel) }
            }
            .launchIn(viewModelScope)
    }

    private fun loadData() {
        viewModelScope.launch {
            withDelayedLoading(onLoading = {
                updateState { copy(isLoading = it) }
            }) {
                transcriptUseCase.getTranscript().fold(
                    onSuccess = { result ->
                        updateState {
                            copy(
                                gpa = TranscriptMapper.getGpa(result),
                                passedCredits = TranscriptMapper.getTotalCredit(result)
                            )
                        }
                    },
                    onFailure = { error ->
                        Log.e("123123", "loadData: $error")
                        updateState { copy(error = error.message) }
                    }
                )
            }
        }
    }

    private fun updateState(block: TranscriptState.() -> TranscriptState) {
        _uiState.value = _uiState.value.block()
    }

    fun refreshData() {
        viewModelScope.launch {
            withDelayedLoading(onLoading = {
                updateState { copy(isRefreshing = it) }
            }) {
                transcriptUseCase.getTranscript().fold(
                    onSuccess = {
                        updateState {
                            copy(
                                gpa = TranscriptMapper.getGpa(it),
                                passedCredits = TranscriptMapper.getTotalCredit(it)
                            )
                        }
                    },
                    onFailure = { error ->
                        Log.e("123123", "loadData: $error")
                        updateState { copy(error = error.message) }
                    }
                )
            }
        }
    }
}