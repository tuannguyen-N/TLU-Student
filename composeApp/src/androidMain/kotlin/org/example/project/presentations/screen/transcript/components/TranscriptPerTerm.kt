package org.example.project.presentations.screen.transcript.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.SemesterUiModel
import org.example.project.domain.model.SubjectResultUiModel
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun TranscriptPerTerm(
    modifier: Modifier = Modifier,
    academicYear: String,
    semesters: List<SemesterUiModel>,
    onOpenTranscriptTerm: (SemesterUiModel) -> Unit = {},
) {
    Column(
        modifier = modifier.padding(bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        semesters.forEachIndexed { _, semester ->
            key(semester.semesterLabel) {
                val subjectNames =
                    remember(semester.subjects) { semester.subjects.map { it.subjectName } }
                val onOpen = remember(semester) { { onOpenTranscriptTerm(semester) } }

                SubjectCard(
                    onOpenTranscriptTerm = onOpen,
                    academicYear = academicYear,
                    termNumber = semester.semesterLabel,
                    subjects = subjectNames,
                    score = semester.semesterGpa,
                    credits = semester.creditsPassed,
                )
            }
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 0.5.dp,
                color = LocalExtendedColors.current.gray
            )
            Text(
                text = academicYear,
                color = LocalExtendedColors.current.gray,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                thickness = 0.5.dp,
                color = LocalExtendedColors.current.gray
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTranscriptPerTerm() {
    val semesters = listOf(
        SemesterUiModel(
            semesterLabel = "Học kỳ 1",
            semesterGpa = 3.2,
            creditsPassed = 18,
            academicYear = "2023 - 2024",
            subjects = listOf(
                SubjectResultUiModel(
                    subjectName = "Lập trình Android",
                    subjectCode = "IT101",
                    credits = 3,
                    attendanceScore = 9.0,
                    midtermScore = 8.5,
                    finalScore = 8.0,
                    score10 = 8.3,
                    score4 = 3.2,
                    letterGrade = "B+",
                    isPass = true
                ),
                SubjectResultUiModel(
                    subjectName = "Cấu trúc dữ liệu",
                    subjectCode = "IT102",
                    credits = 4,
                    attendanceScore = 8.0,
                    midtermScore = 7.5,
                    finalScore = 7.0,
                    score10 = 7.5,
                    score4 = 3.0,
                    letterGrade = "B",
                    isPass = true
                ),
                SubjectResultUiModel(
                    subjectName = "Cấu trúc dữ liệu",
                    subjectCode = "IT102",
                    credits = 4,
                    attendanceScore = 8.0,
                    midtermScore = 7.5,
                    finalScore = 7.0,
                    score10 = 7.5,
                    score4 = 3.0,
                    letterGrade = "B",
                    isPass = true
                ),
                SubjectResultUiModel(
                    subjectName = "Cấu trúc dữ liệu",
                    subjectCode = "IT102",
                    credits = 4,
                    attendanceScore = 8.0,
                    midtermScore = 7.5,
                    finalScore = 7.0,
                    score10 = 7.5,
                    score4 = 3.0,
                    letterGrade = "B",
                    isPass = true
                )
            )
        ),
        SemesterUiModel(
            semesterLabel = "Học kỳ 2",
            semesterGpa = 3.6,
            creditsPassed = 20,
            academicYear = "2023 - 2024",
            subjects = listOf(
                SubjectResultUiModel(
                    subjectName = "Trí tuệ nhân tạo",
                    subjectCode = "IT201",
                    credits = 3,
                    attendanceScore = 9.5,
                    midtermScore = 9.0,
                    finalScore = 9.0,
                    score10 = 9.2,
                    score4 = 3.8,
                    letterGrade = "A",
                    isPass = true
                ),
                SubjectResultUiModel(
                    subjectName = "Cơ sở dữ liệu",
                    subjectCode = "IT202",
                    credits = 3,
                    attendanceScore = 8.5,
                    midtermScore = 8.0,
                    finalScore = 8.5,
                    score10 = 8.4,
                    score4 = 3.5,
                    letterGrade = "A-",
                    isPass = true
                )
            )
        )
    )

    TranscriptPerTerm(
        academicYear = "2023 - 2024",
        semesters = semesters
    )
}