package org.example.project.presentations.screen.exam_schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.repository.ExamScheduleRepository
import org.example.project.domain.usecase.SemesterUseCase

class ExamScheduleViewModelFactory(
    private val examScheduleRepository: ExamScheduleRepository,
    private val semesterUseCase: SemesterUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ExamScheduleViewModel::class.java)) {
            return ExamScheduleViewModel(
                semesterUseCase = semesterUseCase,
                examScheduleRepository = examScheduleRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}