package org.example.project.presentations.screen.feature_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.example.project.presentations.utils.SearchIndex
import org.example.project.presentations.utils.SearchableFeature
import org.example.project.presentations.utils.scoreFeature

@OptIn(FlowPreview::class)
class SearchViewModel : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    private val threshold = 0.80

    val results: StateFlow<List<SearchableFeature>> = _query
        .debounce(250)
        .map { q ->
            if (q.isBlank()) return@map emptyList()
            SearchIndex.features
                .map { it to scoreFeature(q, it) }
                .filter { (_, score) -> score >= threshold }
                .sortedByDescending { (_, score) -> score }
                .map { (feature, _) -> feature }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }
}