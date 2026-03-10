package org.example.project.presentations.screen.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import org.example.project.domain.model.TimetableUiState
import org.example.project.domain.usecase.ScheduleUseCase
import org.example.project.presentations.utils.getCurrentWeek

class TimetableViewModel(
    private val scheduleUseCase: ScheduleUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TimetableUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<TimetableUIEvent>()
    val events = _events.receiveAsFlow()

    init {
        observeWeekSchedule()
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            updateState { copy(isLoading = true) }
            scheduleUseCase.getWeakSchedule(getCurrentWeek().first, getCurrentWeek().second).fold(
                onSuccess = { updateState { copy(isLoading = false) } },
                onFailure = { updateState { copy(isLoading = false) } } // TODO: handle fail 
            )
        }
    }

    private fun observeWeekSchedule() {
        scheduleUseCase.weakSchedule.onEach {
            it?.let { data ->
                updateState { copy(weekSchedule = data) }
            }
        }
            .launchIn(viewModelScope)
    }

    private fun updateState(newState: TimetableUiState.() -> TimetableUiState) {
        _uiState.value = _uiState.value.newState()
    }
}