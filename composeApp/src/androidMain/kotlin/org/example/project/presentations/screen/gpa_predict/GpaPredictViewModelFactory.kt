package org.example.project.presentations.screen.gpa_predict

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.usecase.GpaPredictUseCase
import org.example.project.domain.usecase.ScheduleUseCase
import org.example.project.domain.usecase.SemesterUseCase
import org.example.project.domain.usecase.TranscriptUseCase

class GpaPredictViewModelFactory(
    private val semesterUseCase: SemesterUseCase,
    private val transcriptUseCase: TranscriptUseCase,
    private val scheduleUseCase: ScheduleUseCase,
    private val gpaPredictUseCase: GpaPredictUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(GpaPredictViewModel::class.java)) {
            return GpaPredictViewModel(
                semesterUseCase = semesterUseCase,
                transcriptUseCase = transcriptUseCase,
                scheduleUseCase = scheduleUseCase,
                gpaPredictUseCase = gpaPredictUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}