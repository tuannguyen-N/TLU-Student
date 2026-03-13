package org.example.project.presentations.screen.timetable.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.data.mapper.toSemesterStringList
import org.example.project.data.mapper.toWeekDateList
import org.example.project.data.remote.dto.semester.Semester
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.data.remote.dto.week_schedule.Lecturer
import org.example.project.domain.model.TimetableUiState
import org.example.project.presentations.components.LoadingView
import org.example.project.presentations.components.TopScreenBar
import org.example.project.presentations.dialog.TeacherDetailInfoDialog
import org.example.project.presentations.screen.school_schedule.components.ClassDetailBottomSheet
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.utils.toSlashDate

@Composable
fun TimetableContent(
    uiState: TimetableUiState,
    onBack: () -> Unit,
    onNextWeekSchedule: () -> Unit,
    onPreviousWeekSchedule: () -> Unit,
    onShowSubjectDetail: (CourseClass) -> Unit,
    onContact: (String) -> Unit,
    onOpenDetailLecturerInfo: () -> Unit,
    onDismissDetailCourseClass: () -> Unit,
    onDismissDetailLecturerInfo: () -> Unit,
    onCopyLecturerCode: (String, String) -> Unit,
    onCopyPhoneNumber: (String, String) -> Unit,
    onCopyEmail: (String, String) -> Unit,
    onChangeSemester: (String) -> Unit,
    onChangeWeek: (String) -> Unit,
    onToggleDropDown: () -> Unit
) {
    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        topBar = {
            TopScreenBar(
                title = "Thời khoá biểu",
                onBack = onBack,
                schoolYears = uiState.semesters.toSemesterStringList(),
                onClicked = {
                    onChangeSemester(it)
                },
                yearValue = uiState.selectedSemester?.semesterName ?: "2026"
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                WeekView(
                    modifier = Modifier.padding(10.dp),
                    week = "Tuần ${uiState.weekSchedule?.week}",
                    weekDate = "${uiState.weekSchedule?.startDate?.toSlashDate()} - ${uiState.weekSchedule?.endDate?.toSlashDate()}",
                    onClickNextWeek = onNextWeekSchedule,
                    onClickPreviousWeek = onPreviousWeekSchedule,
                    onClickWeekLabel = onToggleDropDown
                )

                if (uiState.showWeekMenu) {
                    DropDownPopup(
                        items = uiState.selectedSemester?.toWeekDateList() ?: emptyList(),
                        selectedItem = uiState.selectedWeek,
                        onItemSelected = { onChangeWeek(it) },
                        onDismiss = onToggleDropDown
                    )
                }
            }

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

@Preview(showBackground = true)
@Composable
fun TimetableLoadingPreview() {
    TimetableContent(
        uiState = TimetableUiState(
            semesters = listOf(
                Semester(
                    semesterName = "Học kỳ 1 - 2024-2025",
                    startDate = "02/09/2024",
                    endDate = "05/01/2025"
                ),
                Semester(
                    semesterName = "Học kỳ 2 - 2024-2025",
                    startDate = "06/01/2025",
                    endDate = "01/06/2025"
                ),
                Semester(
                    semesterName = "Học kỳ 1 - 2023-2024",
                    startDate = "04/09/2023",
                    endDate = "07/01/2024"
                )
            ),
            selectedSemester = Semester(
                semesterName = "Học kỳ 1 - 2024-2025",
                startDate = "02/09/2024",
                endDate = "05/01/2025"
            ),
            weekSchedule = null,
            showDetailCourseClass = false,
            showDetailLecturerInfo = false,
            showWeekMenu = true,
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
        onChangeSemester = {},
        onToggleDropDown = {},
        onChangeWeek = {}
    )
}