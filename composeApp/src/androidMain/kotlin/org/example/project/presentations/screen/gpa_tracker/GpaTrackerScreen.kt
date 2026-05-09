package org.example.project.presentations.screen.gpa_tracker

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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

    StatusBarStyle(true)

    Scaffold(
        topBar = {
            TopCenterScreenBar(
                title = "Thống kê GPA",
                backgroundColor = Color.White,
                contentColor = Color.Black,
                onBack = onBack
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