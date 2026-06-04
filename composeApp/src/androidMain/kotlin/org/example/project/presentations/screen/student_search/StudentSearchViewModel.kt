package org.example.project.presentations.screen.student_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.remote.dto.student_search.StudentSummary
import org.example.project.domain.model.AppResult
import org.example.project.domain.repository.SearchHistoryRepository
import org.example.project.domain.usecase.StudentUseCase

class StudentSearchViewModel(
    private val studentUseCase: StudentUseCase,
    private val searchHistoryRepository: SearchHistoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(StudentSearchState())
    val uiState = _uiState.asStateFlow()

    private val searchQuery = MutableStateFlow("")

    init {
        observeSearchHistory()
        observeSearch()
    }

    private fun observeSearchHistory() {
        viewModelScope.launch {

            studentUseCase.studentInfo
                .filterNotNull()
                .map { it.studentCode.lowercase() }
                .distinctUntilChanged()
                .collectLatest { studentId ->
                    searchHistoryRepository
                        .observeSearchHistory(studentId)
                        .collect { histories ->
                            _uiState.update { it.copy(recentSearches = histories) }
                        }
                }
        }
    }

    private fun observeSearch() {
        searchQuery
            .debounce(500)
            .distinctUntilChanged()
            .onEach { query ->

                if (query.isBlank()) {
                    _uiState.update { it.copy(searchResults = emptyList()) }
                    return@onEach
                }

                when (
                    val result = studentUseCase.searchStudents(keyword = query)
                ) {
                    is AppResult.Success -> {
                        val students = result.data.content.map {
                            StudentSummary(
                                studentCode = it.studentCode,
                                fullName = it.fullName
                            )
                        }

                        _uiState.update { it.copy(searchResults = students) }
                    }

                    is AppResult.Failure -> {
                        _uiState.update { it.copy(searchResults = emptyList()) }
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        searchQuery.value = query
    }

    fun onClearQuery() {
        _uiState.update { it.copy(searchQuery = "", searchResults = emptyList()) }
        searchQuery.value = ""
    }

    fun onRemoveRecentSearch(search: String) {
        viewModelScope.launch {
            val studentId =
                studentUseCase.studentInfo.value
                    ?.studentCode
                    ?.lowercase()
                    ?: return@launch

            searchHistoryRepository.removeSearchHistory(
                userId = studentId,
                keyword = search
            )
        }
    }

    fun onClearAllRecentSearches() {
        viewModelScope.launch {
            val studentId =
                studentUseCase.studentInfo.value
                    ?.studentCode
                    ?.lowercase()
                    ?: return@launch

            searchHistoryRepository.clearSearchHistory(studentId)
        }
    }

    fun onSearch() {
        val query = uiState.value.searchQuery.trim()

        if (query.isBlank()) return

        viewModelScope.launch {
            val studentId =
                studentUseCase.studentInfo.value
                    ?.studentCode
                    ?.lowercase()
                    ?: return@launch

            searchHistoryRepository.saveSearchHistory(
                userId = studentId,
                keyword = query
            )
        }
    }
}