package org.example.project.presentations.screen.timetable

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import org.example.project.domain.model.TimetableUiState

class TimetableViewModel: ViewModel() {
    var state by mutableStateOf(TimetableUiState())
        private set

    private val _events = Channel<TimetableUIEvent>()
    val events = _events.receiveAsFlow()

    fun onDismissSubjectDetailDialog(){
        state = state.copy(isShowDetailSubjectDialog = false)
    }
}