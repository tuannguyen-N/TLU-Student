package org.example.project.presentations.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.data.remote.interceptor.AuthPluginConfig
import org.example.project.domain.repository.ExamScheduleRepository
import org.example.project.domain.repository.FeatureRepository
import org.example.project.domain.repository.NewsRepository
import org.example.project.domain.repository.NotificationRepository
import org.example.project.domain.repository.QuoteRepository
import org.example.project.domain.repository.UserRepository
import org.example.project.domain.usecase.ScheduleUseCase
import org.example.project.domain.usecase.SemesterUseCase
import org.example.project.domain.usecase.StudentUseCase

class HomeViewModelFactory(
    private val studentUseCase: StudentUseCase,
    private val scheduleUseCase: ScheduleUseCase,
    private val featureRepository: FeatureRepository,
    private val newsRepository: NewsRepository,
    private val quoteRepository: QuoteRepository,
    private val authPluginConfig: AuthPluginConfig,
    private val notificationRepository: NotificationRepository,
    private val examScheduleRepository: ExamScheduleRepository,
    private val semesterUseCase: SemesterUseCase,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                studentUseCase,
                scheduleUseCase,
                featureRepository,
                newsRepository,
                quoteRepository,
                authPluginConfig,
                notificationRepository,
                examScheduleRepository,
                semesterUseCase,
                userRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}