package org.example.project.presentations.screen.school_schedule

import android.content.ClipData
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.example.project.R
import org.example.project.presentations.components.AppTopBar
import org.example.project.presentations.dialog.TeacherDetailInfoDialog
import org.example.project.presentations.screen.school_schedule.components.ClassDetailBottomSheet
import org.example.project.presentations.screen.school_schedule.components.DateList
import org.example.project.presentations.screen.school_schedule.components.TodayScheduleList
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel,
    onOpenNotificationScreen: () -> Unit = {},
    onOpenTimetable: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()

    fun copyToClipboard(text: String, label: String) {
        scope.launch {
            clipboard.setClipEntry(
                ClipEntry(
                    ClipData.newPlainText(label, text)
                )
            )
            Toast.makeText(context, "Đã sao chép $label", Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        topBar = {
            AppTopBar(
                iconRes = R.drawable.icon_school_schedule,
                title = "Lịch Học",
                onOpenNotificationScreen = onOpenNotificationScreen
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            DateList(
                modifier = Modifier.padding(top = 5.dp),
                selectedDayOfWeek = uiState.selectedDayOfWeek,
                currentDay = uiState.currentDay,
                onChangeDayOfWeek = { day ->
                    viewModel.onChangeDayOfWeek(day)
                }
            )

            TodayScheduleList(
                modifier = Modifier.padding(top = 20.dp),
                onOpenTimetable = onOpenTimetable,
                courseClasses = uiState.courseClasses ?: emptyList(),
                onOpenDetailCourseClass = viewModel::onOpenDetailCourseClass,
                onClickViewTomorrow = viewModel::onClickViewTomorrow
            )
        }

        if (uiState.showDetailCourseClass) {
            ClassDetailBottomSheet(
                onDismiss = viewModel::onDismissDetailCourseClass,
                courseClass = uiState.selectedCourseClass!!,
                onViewMaterials = {
                    // TODO:  
                },
                onOpenDetailLecturerInfo = viewModel::onOpenDetailLecturerInfo
            )
        }

        if (uiState.showDetailLecturerInfo) {
            val lecturer = uiState.selectedCourseClass?.lecturer ?: return@Scaffold
            TeacherDetailInfoDialog(
                lecturer = lecturer,
                onDismiss = viewModel::onDismissDetailLecturerInfo,
                onContact = {},
                onCopyPhoneNumber = {
                    copyToClipboard(
                        lecturer.phoneNumber.orEmpty(),
                        "số điện thoại"
                    )
                },
                onCopyLecturerCode = {
                    copyToClipboard(
                        lecturer.lecturerCode.orEmpty(),
                        "mã giảng viên"
                    )
                },
                onCopyEmail = { copyToClipboard(lecturer.email, "email") }
            )
        }
    }
}