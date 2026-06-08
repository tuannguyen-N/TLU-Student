package org.example.project.presentations.screen.digital_student_card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.data.remote.interceptor.AuthPluginConfig
import org.example.project.domain.usecase.CountdownTimerUseCase
import org.example.project.domain.usecase.GenerateQrUseCase
import org.example.project.domain.usecase.StudentUseCase

class DigitalStudentCardViewModelFactory(
    private val studentUseCase: StudentUseCase,
    private val timerUseCase: CountdownTimerUseCase,
    private val generateQrUseCase: GenerateQrUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(DigitalStudentCardViewModel::class.java)) {
            return DigitalStudentCardViewModel(
                studentUseCase,
                timerUseCase,
                generateQrUseCase,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}