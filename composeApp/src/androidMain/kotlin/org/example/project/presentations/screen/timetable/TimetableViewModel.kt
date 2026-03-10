package org.example.project.presentations.screen.timetable

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import org.example.project.domain.model.TimetableUiState

class TimetableViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(TimetableUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<TimetableUIEvent>()
    val events = _events.receiveAsFlow()

    init {
        loadData()
    }

    private fun loadData(){

    }
}