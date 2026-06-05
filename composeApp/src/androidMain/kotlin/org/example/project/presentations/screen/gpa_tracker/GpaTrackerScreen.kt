package org.example.project.presentations.screen.gpa_tracker

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DownloadForOffline
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.domain.model.ExportState
import org.example.project.domain.model.ExportedFile
import org.example.project.presentations.components.StatusBarStyle
import org.example.project.presentations.components.TopCenterScreenBar
import org.example.project.presentations.screen.gpa_tracker.components.GpaSemester
import org.example.project.presentations.screen.gpa_tracker.components.GpaTrackerContent
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun GpaTrackerScreen(
    viewModel: GpaTrackerViewModel,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val transcript = uiState.transcript ?: return
    val context = LocalContext.current

    val allSemesters = transcript.academicYearGroups
        .flatMap { it.semesters }

    val currentGpa = allSemesters.lastOrNull()?.semesterGpa ?: 0.0

    val gpaDelta = if (allSemesters.size >= 2) {
        currentGpa - allSemesters[allSemesters.size - 2].semesterGpa
    } else 0.0

    val highestSemester = allSemesters.maxByOrNull { it.semesterGpa }
    val lowestSemester = allSemesters.minByOrNull { it.semesterGpa }

    val semesterData = allSemesters.map { sem ->
        GpaSemester(
            label = sem.semesterLabel,
            gpa = sem.semesterGpa
        )
    }

    LaunchedEffect(uiState.exportState) {
        when (val state = uiState.exportState) {
            is ExportState.Success -> {
                saveFileToDownloads(context, state.file)
                Toast.makeText(context, "Đã lưu vào thư mục Downloads", Toast.LENGTH_SHORT).show()
                viewModel.resetExportState()
            }

            is ExportState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                viewModel.resetExportState()
            }

            else -> Unit
        }
    }

    StatusBarStyle(true)

    Scaffold(
        topBar = {
            TopCenterScreenBar(
                title = "Thống kê GPA",
                backgroundColor = Color.White,
                contentColor = Color.Black,
                onBack = onBack,
                icon = Icons.Default.DownloadForOffline,
                onClickAction = { viewModel.exportTranscript() },
                enableActionButton = true,
                isLoading = uiState.exportState is ExportState.Loading
            )
        },
        contentWindowInsets = WindowInsets(0),
        containerColor = LocalExtendedColors.current.background
    ) { innerPadding ->
        GpaTrackerContent(
            currentGpa = currentGpa,
            gpaDelta = gpaDelta,
            highestGpa = highestSemester?.semesterGpa ?: 0.0,
            highestSemester = highestSemester?.semesterLabel.orEmpty(),
            lowestGpa = lowestSemester?.semesterGpa ?: 0.0,
            lowestSemester = lowestSemester?.semesterLabel.orEmpty(),
            semesterData = semesterData,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

private fun saveFileToDownloads(context: Context, file: ExportedFile) {
    val resolver = context.contentResolver
    val values = ContentValues().apply {
        put(MediaStore.Downloads.DISPLAY_NAME, file.fileName)
        put(
            MediaStore.Downloads.MIME_TYPE,
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        )
        put(MediaStore.Downloads.IS_PENDING, 1)
    }
    val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values) ?: return
    } else {
        TODO("VERSION.SDK_INT < Q")
    }
    resolver.openOutputStream(uri)?.use { it.write(file.bytes) }
    values.clear()
    values.put(MediaStore.Downloads.IS_PENDING, 0)
    resolver.update(uri, values, null, null)
}