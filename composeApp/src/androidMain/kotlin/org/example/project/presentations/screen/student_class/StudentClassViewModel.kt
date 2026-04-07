package org.example.project.presentations.screen.student_class

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.domain.repository.StudentClassRepository
import org.example.project.presentations.utils.withDelayedLoading

class StudentClassViewModel(
    private val studentClassRepository: StudentClassRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(StudentClassState())
    val uiState = _uiState.asStateFlow()

    init {
        loadingData()
    }

    private fun loadingData() {
        viewModelScope.launch {
            withDelayedLoading(onLoading = { updateState { copy(isLoading = it) } }) {
                studentClassRepository.getStudentClassInfo().fold(
                    onSuccess = {
                        updateState { copy(studentClassInfoData = it) }
                    },
                    onFailure = {
                        Log.e("123123", "loadingData: $it")
                    }
                )
            }
        }
    }

    private fun updateState(newState: StudentClassState.() -> StudentClassState) {
        _uiState.value = _uiState.value.newState()
    }
}