package org.example.project.presentations.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.example.project.domain.model.HomeUiEvent
import org.example.project.domain.repository.FeatureRepository
import org.example.project.domain.usecase.ScheduleUseCase
import org.example.project.domain.usecase.StudentUseCase
import org.example.project.presentations.utils.getTodayDayOfWeek
import org.example.project.presentations.utils.nearestClasses
import org.example.project.presentations.utils.withDelayedLoading
import kotlin.time.Clock

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
        scheduleUseCase.daySchedule
            .map { cache ->
                cache[getTodayDayOfWeek()]
            }
            .map { data ->
                val currentTime = Clock.System.now()
                    .toLocalDateTime(TimeZone.currentSystemDefault())
                    .time
                data?.courseClasses?.nearestClasses(currentTime)
            }
            .onEach { filteredList ->
                filteredList?.let {
                    updateState {
                        copy(courseClasses = it, loadingScheduleClassList = false)
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
        viewModelScope.launch { featureRepository.seedDefaultsIfNeeded() }
        viewModelScope.launch { loadStudentInfo() }
        viewModelScope.launch { loadCourseClasses() }
    }

    private suspend fun loadCourseClasses() {
        withDelayedLoading(
            onLoading = { updateState { copy(loadingScheduleClassList = it) } }
        ) {
            scheduleUseCase.getDaySchedule(getTodayDayOfWeek())
        }
    }

    private suspend fun loadStudentInfo() {
        withDelayedLoading(
            onLoading = { updateState { copy(loadingStudentInfo = it) } }
        ) {
            studentUseCase.getStudentInfo().fold(
                onSuccess = {},
                onFailure = {
                    Log.e("123123", "loadStudentInfo: $it", )
                }
            )
        }
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