package org.example.project.presentations.screen.timetable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.presentations.components.TopScreenBar
import org.example.project.presentations.dialog.SubjectDetailDialog
import org.example.project.presentations.screen.timetable.components.TimetableScrollableArea
import org.example.project.presentations.screen.timetable.components.WeekView
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun TimetableScreen(
//    viewModel: TimetableViewModel,
    onBack: () -> Unit = {}
){
//    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        topBar = {
            TopScreenBar(
                title = "Thời khoá biểu",
                onBack = onBack
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            WeekView(modifier = Modifier.padding(10.dp))

            TimetableScrollableArea(
                onShowSubjectDetail = {}
            )
        }
    }
}