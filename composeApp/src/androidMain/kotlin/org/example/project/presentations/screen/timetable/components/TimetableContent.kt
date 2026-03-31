package org.example.project.presentations.screen.timetable.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.data.mapper.toDisplayWeekDate
import org.example.project.data.mapper.toSemesterStringList
import org.example.project.data.mapper.toWeekDateList
import org.example.project.data.remote.dto.semester.Semester
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.data.remote.dto.week_schedule.Lecturer
import org.example.project.presentations.components.LoadingView
import org.example.project.presentations.components.TopScreenBar
import org.example.project.presentations.dialog.TeacherDetailInfoDialog
import org.example.project.presentations.screen.school_schedule.components.ClassDetailBottomSheet
import org.example.project.presentations.screen.timetable.TimetableState
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun TimetableContent(
    uiState: TimetableState,
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
                values = uiState.semesters.toSemesterStringList(),
                onClickItem = {
                    onChangeSemester(it)
                },
                enableListItem = true,
                value = uiState.selectedSemester?.semesterName ?: "2026"
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
                    weekDate = uiState.weekSchedule?.toDisplayWeekDate() ?: "",
                    onClickNextWeek = onNextWeekSchedule,
                    onClickPreviousWeek = onPreviousWeekSchedule,
                    onClickWeekLabel = onToggleDropDown
                )

                if (uiState.showWeekMenu) {
                    DropDownPopup(
                        items = uiState.selectedSemester?.toWeekDateList() ?: emptyList(),
                        selectedItem = uiState.selectedWeek,
                        onClickItem = { onChangeWeek(it) },
                        onDismiss = onToggleDropDown,
                        alignment = Alignment.TopCenter,
                        width = 220.dp,
                        modifier = Modifier.fillMaxWidth(),
                        itemDisplay = { it.toDisplayWeekDate() }
                    )
                }
            }

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