package org.example.project.presentations.screen.temp_timetable.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.presentations.components.LoadingView
import org.example.project.presentations.components.TopScreenBar
import org.example.project.presentations.dialog.TeacherDetailInfoDialog
import org.example.project.presentations.screen.school_schedule.components.ClassDetailBottomSheet
import org.example.project.presentations.screen.timetable.TimetableState
import org.example.project.presentations.screen.timetable.components.TimetableScrollableArea
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun TempTimetableContent(
    uiState: TimetableState,
    onBack: () -> Unit,
    onShowSubjectDetail: (CourseClass) -> Unit,
    onContact: (String) -> Unit,
    onOpenDetailLecturerInfo: () -> Unit,
    onDismissDetailCourseClass: () -> Unit,
    onDismissDetailLecturerInfo: () -> Unit,
    onCopyLecturerCode: (String, String) -> Unit,
    onCopyPhoneNumber: (String, String) -> Unit,
    onCopyEmail: (String, String) -> Unit,
    onViewMaterials: () -> Unit,
) {
    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        topBar = {
            TopScreenBar<String>(
                title = "Thời khoá biểu tạm thời",
                onBack = onBack,
                enableListItem = false
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            HorizontalDivider(thickness = 0.5.dp, color = LocalExtendedColors.current.gray)

            TimetableScrollableArea(
                dailySchedules = uiState.weekSchedule?.dailySchedules ?: emptyList(),
                onShowSubjectDetail = { onShowSubjectDetail(it) }
            )
        }

        if (uiState.isLoading) {
            LoadingView()
        }
        if (uiState.showDetailCourseClass) {
            ClassDetailBottomSheet(
                onDismiss = onDismissDetailCourseClass,
                courseClass = uiState.selectedCourseClass!!,
                onViewMaterials = onViewMaterials,
                onOpenDetailLecturerInfo = onOpenDetailLecturerInfo
            )
        }

        if (uiState.showDetailLecturerInfo) {
            val lecturer = uiState.selectedCourseClass?.lecturer ?: return@Scaffold
            TeacherDetailInfoDialog(
                lecturer = lecturer,
                onDismiss = onDismissDetailLecturerInfo,
                onContact = { onContact(lecturer.email) },
                onCopyLecturerCode = {
                    onCopyLecturerCode(
                        lecturer.lecturerCode.orEmpty(),
                        "mã giảng viên"
                    )
                },
                onCopyPhoneNumber = {
                    onCopyPhoneNumber(
                        lecturer.phoneNumber.orEmpty(),
                        "số điện thoại"
                    )
                },
                onCopyEmail = { onCopyEmail(lecturer.email, "email") }
            )
        }
    }
}