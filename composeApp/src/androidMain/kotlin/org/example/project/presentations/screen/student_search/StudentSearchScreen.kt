package org.example.project.presentations.screen.student_search

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.presentations.screen.student_search.components.StudentSearchContent

@Composable
fun StudentSearchScreen(
    viewModel: StudentSearchViewModel,
    onBack: () -> Unit,
    onOpenMessage: (String, String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    StudentSearchContent(
        modifier = Modifier.statusBarsPadding(),
        uiState = uiState,
        onBack = onBack,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onClearQuery = viewModel::onClearQuery,
        onRemoveRecentSearch = viewModel::onRemoveRecentSearch,
        onClearAllRecentSearches = viewModel::onClearAllRecentSearches,
        onOpenMessage = onOpenMessage,
        onSearch = viewModel::onSearch,
        onClickRecentSearch = viewModel::onRecentSearchClick
    )
}