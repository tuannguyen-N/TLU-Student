package org.example.project.presentations.screen.student_class

import android.content.ClipData
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.screen.student_class.components.StudentClassContent

@Composable
fun StudentClassScreen(
    viewModel: StudentClassViewModel,
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()

    val copyToClipboard: (String, String) -> Unit = { text, label ->
        scope.launch {
            clipboard.setClipEntry(ClipEntry(ClipData.newPlainText(label, text)))
        }
    }

    StatusBarStyle(darkIcons = true)

    StudentClassContent(
        uiState = uiState,
        onBack = onBack,
        onCopy = copyToClipboard
    )
}