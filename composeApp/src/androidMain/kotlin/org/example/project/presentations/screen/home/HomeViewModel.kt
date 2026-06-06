package org.example.project.presentations.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.mapper.getTodayDayOfWeek
import org.example.project.data.remote.dto.student_search.StudentSummary
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
import org.example.project.presentations.utils.withDelayedLoading

class HomeViewModel(
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
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState = combine(
        _uiState,
        featureRepository.getQuickAccessList()
    ) { state, quickAccessList ->
        state.copy(quickAccessList = quickAccessList)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeState()
    )

    init {
        observeStudentInfo()
        observeReadNotifications()
        observeAlerts()
        observeSemester()
        loadInitData()
    }

    private fun observeSemester() {
        semesterUseCase.semesters.onEach { semesterList ->
            updateState { copy(currentSemester = semesterList?.lastOrNull()) }
        }.launchIn(viewModelScope)
    }

    private fun observeAlerts() {
        notificationRepository.getAlertList(studentUseCase.studentInfo.value?.studentCode ?: "")
            .onEach {
                updateState { copy(alerts = it) }
            }
            .launchIn(viewModelScope)
    }

    private fun observeReadNotifications() {
        notificationRepository.hasUnreadNotifications.onEach { hasUnread ->
            updateState { copy(isAllNotificationsRead = !hasUnread) }
        }.launchIn(viewModelScope)
    }

    private fun observeStudentInfo() {
        studentUseCase.studentInfo
            .onEach {
                it?.let { info ->
                    updateState { copy(studentInfo = info, loadingStudentInfo = false) }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun loadInitData() {
        viewModelScope.launch { featureRepository.seedDefaultsIfNeeded() }
        viewModelScope.launch { loadSemester() }
        viewModelScope.launch { loadStudentInfo() }
        viewModelScope.launch { loadCourseClasses() }
        viewModelScope.launch { loadNews() }
        viewModelScope.launch { loadDailyQuote() }
        viewModelScope.launch { loadAlert() }
        viewModelScope.launch { loadExamDaySchedule() }
        loadImage()
    }

    private suspend fun loadExamDaySchedule() {
        examScheduleRepository.getExamDaySchedule(
            _uiState.value.currentSemester?.semesterCode ?: ""
        ).onSuccess {
            updateState { copy(examDayScheduleList = it) }
        }
    }

    private suspend fun loadSemester() {
        semesterUseCase.getSemesters(true)
    }

    private suspend fun loadAlert() {
        withDelayedLoading(
            onLoading = { updateState { copy(loadingAlertList = it) } }
        ) {
            notificationRepository.getNotifications(true)
        }
    }

    private suspend fun loadDailyQuote() {
        val dailyQuote = quoteRepository.getDailyQuote()
        updateState { copy(dailyQuote = dailyQuote) }
    }

    private fun loadImage() {
        val imageBase64 = authPluginConfig.imageStorage.getImageBase64()
        updateState { copy(imageBase64 = imageBase64) }
    }

    private suspend fun loadNews() {
        withDelayedLoading(
            onLoading = { updateState { copy(loadingEventList = it) } }
        ) {
            newsRepository.getTop5News().fold(
                onSuccess = { updateState { copy(newsAndEvents = it) } },
                onFailure = { Log.e("123123", "loadNews: $it") }
            )
        }
    }

    private suspend fun loadCourseClasses() {
        withDelayedLoading(
            onLoading = { updateState { copy(loadingScheduleClassList = it) } }
        ) {
            delay(400L)
            scheduleUseCase.getDaySchedule(getTodayDayOfWeek()).onSuccess {
                updateState { copy(dayScheduleList = it) }
            }
        }
    }

    private suspend fun loadStudentInfo() {
        withDelayedLoading(
            onLoading = { updateState { copy(loadingStudentInfo = it) } }
        ) {
            studentUseCase.getStudentInfo().onSuccess {
                viewModelScope.launch {
                    userRepository.uploadUser(
                        StudentSummary(
                            studentCode = it.studentCode,
                            fullName = it.fullName
                        )
                    )
                }
            }
        }
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.RefreshData -> refreshData()
        }
    }

    private fun refreshData() {
        viewModelScope.launch {
            updateState { copy(isRefreshing = true) }
            try {
                loadCourseClasses()
                loadAlert()
            } finally {
                delay(400)
                updateState { copy(isRefreshing = false) }
            }
        }
    }

    private fun updateState(newState: HomeState.() -> HomeState) {
        _uiState.update(newState)
    }
}