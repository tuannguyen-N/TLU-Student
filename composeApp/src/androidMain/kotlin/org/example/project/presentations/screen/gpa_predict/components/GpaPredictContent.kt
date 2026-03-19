package org.example.project.presentations.screen.gpa_predict.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.example.project.presentations.components.ButtonView
import org.example.project.presentations.components.TopCenterScreenBar
import org.example.project.presentations.screen.gpa_predict.GpaPredictState
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun GpaPredictContent(
    uiState: GpaPredictState,
    onBack: () -> Unit,
    onMidtermChange: (String, String) -> Unit,
    onFinalChange: (String, String) -> Unit,
    onPredictGpa: () -> Unit,
    onResetData: () -> Unit
) {
    var popupSubjectCode by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(uiState.failedSubjects) {
        popupSubjectCode = null
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopCenterScreenBar(
                title = "Dự đoán điểm GPA",
                onBack = onBack,
                backgroundColor = Color.White,
                contentColor = Color.Black,
                enableActionButton = true,
                icon = Icons.Filled.RestartAlt,
                onClickAction = onResetData
            )
        },
        containerColor = LocalExtendedColors.current.background
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row {
                GpaCard(
                    title = "GPA HIỆN TẠI",
                    gpa = uiState.realGpa,
                    creditsEarned = uiState.totalRealCredit,
                    totalCredits = 140,
                    modifier = Modifier.weight(1f)
                )

                Spacer(Modifier.width(15.dp))

                GpaCard(
                    title = "GPA DỰ KIẾN",
                    gpa = uiState.predictedGpa,
                    creditsEarned = uiState.totalPredictedCredit,
                    totalCredits = 140, // TODO: change total credits 
                    isDark = true,
                    modifier = Modifier.weight(1f)
                )
            }

            SubjectListSection(
                subjects = uiState.subjects,
                onFinalChange = onFinalChange,
                onMidtermChange = onMidtermChange,
                scores = uiState.scores,
                failedSubjects = uiState.failedSubjects,
                popupSubjectCode = popupSubjectCode,
                onTogglePopup = { code ->
                    popupSubjectCode = if (popupSubjectCode == code) null else code
                }
            )

            ImportantNoteCard()

            ButtonView(
                text = "Tính Toán GPA",
                shape = RoundedCornerShape(14.dp),
                enabled = true,
                backgroundColorRes = LocalExtendedColors.current.red,
                onClick = onPredictGpa
            )
        }
    }
}