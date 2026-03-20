package org.example.project.presentations.screen.digital_student_card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.model.QrState
import org.example.project.domain.usecase.CountdownTimerUseCase
import org.example.project.domain.usecase.GenerateQrUseCase
import org.example.project.domain.usecase.StudentUseCase
import org.example.project.presentations.utils.generateQrBitmap
import org.example.project.presentations.utils.toByteArray
import org.example.project.presentations.utils.withDelayedLoading

class DigitalStudentCardViewModel(
    private val studentUseCase: StudentUseCase,
    private val timerUseCase: CountdownTimerUseCase,
    private val generateQrUseCase: GenerateQrUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(DigitalStudentCardState())
    val uiState = _uiState.asStateFlow()

    private var timerJob: Job? = null

    init {
        observeStudentInfo()
        loadData()
    }

    private fun observeStudentInfo() {
        studentUseCase.studentInfo.onEach {
            it?.let { updateState { copy(studentInfo = it) } }
        }.launchIn(viewModelScope)
    }

    private fun loadData() {
        viewModelScope.launch {
            withDelayedLoading(onLoading = { updateState { copy(isLoading = it) } }) {
                studentUseCase.getStudentInfo()
            }
        }
    }

    fun onCreateQr() {
        val studentId = _uiState.value.studentInfo?.studentCode ?: return

        viewModelScope.launch {
            updateState { copy(qrState = QrState.Generating) }

            val qrData = generateQrUseCase() // TODO: truyền studentId

            val qrBytes = generateQrBitmap(qrData).toByteArray()
            updateState {
                copy(qrState = QrState.Active(qrData, qrBytes, 120))
            }
            startTimer()
        }
    }

    fun onBackToFrontCard() {
        cancelTimer()
        updateState { copy(qrState = QrState.Idle) }
    }

    fun onRecreateQr() {
        onCreateQr()
    }

    private fun startTimer() {
        cancelTimer()
        timerJob = timerUseCase.countdown(120)
            .onEach { remaining ->
                updateState {
                    when (val state = qrState) {
                        is QrState.Active -> {
                            if (remaining == 0) {
                                copy(qrState = QrState.Expired(state.qrData))
                            } else {
                                copy(qrState = state.copy(timeLeft = remaining))
                            }
                        }
                        else -> this
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun cancelTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    private fun updateState(newState: DigitalStudentCardState.() -> DigitalStudentCardState) {
        _uiState.update(newState)
    }

    override fun onCleared() {
        super.onCleared()
        cancelTimer()
    }
}