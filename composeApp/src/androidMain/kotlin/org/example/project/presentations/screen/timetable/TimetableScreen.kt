package org.example.project.presentations.screen.timetable

import android.content.ClipData
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.example.project.presentations.screen.timetable.components.TimetableContent

@Composable
fun TimetableScreen(
    viewModel: TimetableViewModel,
    onBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()

    val copyToClipboard: (String, String) -> Unit = { text, label ->
        scope.launch {
            clipboard.setClipEntry(ClipEntry(ClipData.newPlainText(label, text)))
            Toast.makeText(context, "Đã sao chép $label", Toast.LENGTH_SHORT).show()
        }
    }

    TimetableContent(
        uiState = uiState,
        onBack = onBack,
        onNextWeekSchedule = viewModel::onGetNextWeekSchedule,
        onPreviousWeekSchedule = viewModel::onGetPreviousWeekSchedule,
        onShowSubjectDetail = viewModel::onOpenDetailCourseClass,
        onOpenDetailLecturerInfo = viewModel::onOpenDetailLecturerInfo,
        onDismissDetailCourseClass = viewModel::onDismissDetailCourseClass,
        onDismissDetailLecturerInfo = viewModel::onDismissDetailLecturerInfo,
        onCopyPhoneNumber = copyToClipboard,
        onCopyEmail = copyToClipboard,
        onCopyLecturerCode = copyToClipboard,
        onContact = {}
    )
}