package org.example.project.presentations.screen.student_search

import org.example.project.data.remote.dto.student_search.StudentSummary

data class StudentSearchState(
    val searchQuery: String = "",
    val recentSearches: List<String> = emptyList(),
    val searchResults: List<StudentSummary> = emptyList()
) {
    val isSearching: Boolean get() = searchQuery.isNotBlank()
}