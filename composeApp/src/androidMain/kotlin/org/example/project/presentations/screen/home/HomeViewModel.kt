package org.example.project.presentations.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.model.HomeState
import org.example.project.domain.model.HomeUiEvent
import org.example.project.domain.repository.FeatureRepository
import org.example.project.domain.usecase.ScheduleUseCase
import org.example.project.domain.usecase.StudentUseCase
import org.example.project.presentations.utils.getTodayDayOfWeek

class HomeViewModel(
    private val studentUseCase: StudentUseCase,
    private val scheduleUseCase: ScheduleUseCase,
    private val featureRepository: FeatureRepository
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

    private val _event = Channel<HomeUiEvent>()
    val event = _event.receiveAsFlow()

    init {
        observeStudentInfo()
        observeCourseClasses()
        loadInitData()
    }

    private fun observeCourseClasses() {
        scheduleUseCase.dayOfWeekSchedule
            .onEach {
                it?.let { data ->
                    updateState {
                        copy(courseClasses = data.courseClasses, loadingScheduleClassList = false)
                    }
                }
            }
            .launchIn(viewModelScope)
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
        viewModelScope.launch { loadStudentInfo() }
        viewModelScope.launch { loadCourseClasses() }
    }

    private suspend fun loadCourseClasses() {
        updateState { copy(loadingScheduleClassList = true) }
        scheduleUseCase.getDayOfWeekSchedule(getTodayDayOfWeek()).fold(
            onSuccess = { updateState { copy(loadingScheduleClassList = false) } },
            onFailure = { updateState { copy(loadingScheduleClassList = false) } }
        )
    }

    private suspend fun loadStudentInfo() {
        updateState { copy(loadingStudentInfo = true) }
        studentUseCase.getStudentInfo().fold(
            onSuccess = { updateState { copy(loadingStudentInfo = false) } },
            onFailure = { updateState { copy(loadingStudentInfo = false) } }
        )
    }

    fun onAction(action: HomeAction) {
        when (action) {
            else -> Unit
        }
    }

    private fun updateState(newState: HomeState.() -> HomeState) {
        _uiState.update(newState)
    }

    private fun sendUiEvent(event: HomeUiEvent) {
        _event.trySend(event)
    }
}