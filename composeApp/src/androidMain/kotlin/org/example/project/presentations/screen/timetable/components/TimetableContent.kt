package org.example.project.presentations.screen.timetable.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.data.remote.dto.week_schedule.Lecturer
import org.example.project.domain.model.TimetableUiState
import org.example.project.presentations.components.LoadingView
import org.example.project.presentations.components.TopScreenBar
import org.example.project.presentations.dialog.TeacherDetailInfoDialog
import org.example.project.presentations.screen.school_schedule.components.ClassDetailBottomSheet
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun TimetableContent(
    uiState: TimetableUiState,
    onBack: () -> Unit,
    onNextWeekSchedule: () -> Unit,
    onPreviousWeekSchedule: () -> Unit,
    onShowSubjectDetail: (CourseClass) -> Unit,
    onContact: () -> Unit,
    onOpenDetailLecturerInfo: () -> Unit,
    onDismissDetailCourseClass: () -> Unit,
    onDismissDetailLecturerInfo: () -> Unit,
    onCopyLecturerCode: (String, String) -> Unit,
    onCopyPhoneNumber: (String, String) -> Unit,
    onCopyEmail: (String, String) -> Unit
) {
    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        topBar = {
            TopScreenBar(
                title = "Thời khoá biểu",
                onBack = onBack,
                schoolYears = uiState.schoolYears,
                onClickSchoolYear = {},
                yearValue = uiState.currentSelectedYear
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

            WeekView(
                modifier = Modifier.padding(10.dp),
                week = "Tuần ${uiState.weekSchedule?.week}",
                weekDate = "${uiState.weekSchedule?.startDate} - ${uiState.weekSchedule?.endDate}",
                onClickNextWeek = onNextWeekSchedule,
                onClickPreviousWeek = onPreviousWeekSchedule
            )

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
                onViewMaterials = {
                    // TODO:
                },
                onOpenDetailLecturerInfo = onOpenDetailLecturerInfo
            )
        }

        if (uiState.showDetailLecturerInfo) {
            val lecturer = uiState.selectedCourseClass?.lecturer ?: return@Scaffold
            TeacherDetailInfoDialog(
                lecturer = lecturer,
                onDismiss = onDismissDetailLecturerInfo,
                onContact = onContact,
                onCopyLecturerCode = { onCopyLecturerCode(lecturer.lecturerCode.orEmpty(), "mã giảng viên") },
                onCopyPhoneNumber = { onCopyPhoneNumber(lecturer.phoneNumber.orEmpty(), "số điện thoại") },
                onCopyEmail = { onCopyEmail(lecturer.email, "email") }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimetableLoadingPreview() {
    TimetableContent(
        uiState = TimetableUiState(
            showDetailCourseClass = true,
            showDetailLecturerInfo = true,
            selectedCourseClass = CourseClass(
                classCode = "CNTT001",
                dayOfWeek = 2,
                endPeriod = 5,
                endTime = "11:30",
                room = "A1.01",
                startPeriod = 3,
                startTime = "09:00",
                subjectCode = "IT001",
                subjectName = "Lập trình hướng đối tượng",
                lecturer = Lecturer(
                    lecturerCode = "GV12345",
                    fullName = "ThS. Nguyễn Văn Tây",
                    phoneNumber = "0901 234 567",
                    email = "tay.nv@university.edu.vn"
                )
            )
        ),
        onBack = {},
        onNextWeekSchedule = {},
        onPreviousWeekSchedule = {},
        onShowSubjectDetail = {},
        onContact = {},
        onOpenDetailLecturerInfo = {},
        onDismissDetailCourseClass = {},
        onDismissDetailLecturerInfo = {},
        onCopyLecturerCode = { _, _ -> },
        onCopyPhoneNumber = { _, _ -> },
        onCopyEmail = { _, _ -> },
    )
}