package org.example.project.presentations.screen.temp_timetable

import android.content.ClipData
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.screen.temp_timetable.components.TempTimetableContent

@Composable
fun TempTimetableScreen(
    viewModel: TempTimetableViewModel,
    onOpenEmail: (String) -> Unit,
    onBack: () -> Unit = {},
    onViewMaterials: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()

    val copyToClipboard: (String, String) -> Unit = { text, label ->
        scope.launch {
            clipboard.setClipEntry(ClipEntry(ClipData.newPlainText(label, text)))
        }
    }

    StatusBarStyle(darkIcons = false)

    TempTimetableContent(
        uiState = uiState,
        onBack = onBack,
        onShowSubjectDetail = viewModel::onOpenDetailCourseClass,
        onOpenDetailLecturerInfo = viewModel::onOpenDetailLecturerInfo,
        onDismissDetailCourseClass = viewModel::onDismissDetailCourseClass,
        onDismissDetailLecturerInfo = viewModel::onDismissDetailLecturerInfo,
        onCopyPhoneNumber = copyToClipboard,
        onCopyEmail = copyToClipboard,
        onCopyLecturerCode = copyToClipboard,
        onContact = { onOpenEmail(it) },
        onViewMaterials = onViewMaterials
    )
}