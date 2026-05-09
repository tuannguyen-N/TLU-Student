package org.example.project.presentations.screen.class_sign_up.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.example.project.data.remote.dto.enrollment_course_classes.CourseClassEnrollmentData
import org.example.project.data.remote.dto.enrollment_course_classes.Schedule
import org.example.project.presentations.components.ButtonView
import org.example.project.presentations.theme.ExtendedColors
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun SchedulePickerDialog(
    courseTitle: String,
    classGroups: List<CourseClassEnrollmentData>,
    enrolledClassCodes: Set<String> = emptySet(),
    isLoading: Boolean = false,
    onSelect: (CourseClassEnrollmentData) -> Unit,
    onDismiss: () -> Unit
) {
    val color = LocalExtendedColors.current

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(18.dp),
            color = color.white,
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Chọn nhóm học",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = color.fontBlue
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = courseTitle,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = color.gray,
                            )
                        )
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Đóng",
                            tint = Color.Gray
                        )
                    }
                }

                HorizontalDivider(color = color.lightGray)

                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 600.dp)
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (classGroups.isEmpty()) {
                            item {
                                Text(
                                    text = "Không có lớp học",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = color.gray
                                    ),
                                    modifier = Modifier.padding(vertical = 30.dp)
                                )
                            }
                        } else {
                            items(classGroups) { group ->
                                ClassGroupCard(
                                    color = color,
                                    onSelect = { onSelect(group) },
                                    group = group,
                                    isEnrolled = group.classCode in enrolledClassCodes
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ClassGroupCard(
    group: CourseClassEnrollmentData,
    color: ExtendedColors,
    isEnrolled: Boolean = false,
    onSelect: () -> Unit
) {
    val isFull = group.enrolledCount >= group.capacity

    val badgeBackground =
        if (isFull) color.gray.copy(alpha = 0.15f) else color.midBlue.copy(alpha = 0.15f)
    val badgeTextColor = if (isFull) color.gray else color.midBlue
    val badgeLabel = if (isFull) "Đã đầy:" else "Còn lại:"
    val badgeCountColor = if (isFull) color.red else color.midBlue

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = color.white,
        tonalElevation = 0.dp,
        border = BorderStroke(
            width = 1.dp,
            color = if (isEnrolled) color.green else color.gray.copy(alpha = 0.2f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = group.className,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                        )
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.PersonOutline,
                            contentDescription = "Giảng viên",
                            tint = color.gray,
                            modifier = Modifier.size(15.dp)
                        )

                        Text(
                            text = group.lecturerName,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color.Gray,
                            )
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = badgeBackground
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "$badgeLabel ",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = badgeTextColor,
                                fontWeight = FontWeight.Medium,
                            )
                        )
                        Text(
                            text = "${group.enrolledCount}/${group.capacity}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = badgeCountColor,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                group.schedules.forEach { session ->
                    SessionItem(session = session, color = color)
                }
            }

            ButtonView(
                modifier = Modifier
                    .height(40.dp)
                    .width(180.dp),
                textSize = 14.sp,
                enabled = isEnrolled || !isFull,
                text = if (isEnrolled) "Đã đăng ký" else "Chọn",
                backgroundColorRes = if (isEnrolled) color.gray else color.mainBlue,
                onClick = if (isEnrolled) ({}) else onSelect
            )
        }
    }
}

@Composable
fun SessionItem(
    session: Schedule,
    color: ExtendedColors
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        color = color.background
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = null,
                tint = color.mainBlue,
                modifier = Modifier.size(18.dp)
            )
            Column {
                Text(
                    text = "Thứ ${session.dayOfWeek} (Tiết ${session.startPeriod}-${session.endPeriod})",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.SemiBold,
                    )
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "Phòng: " + session.room,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = color.gray,
                            fontWeight = FontWeight.Normal,
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SchedulePickerDialogPreview() {

    val mockClasses = listOf(
        CourseClassEnrollmentData(
            capacity = 60,
            classCode = "CS202-01",
            className = "Nhóm 1",
            enrolledCount = 45,
            id = 1,
            lecturerCode = "GV001",
            lecturerName = "Nguyễn Văn A",
            schedules = listOf(
                Schedule(
                    dayOfWeek = 2,
                    startPeriod = 1,
                    endPeriod = 3,
                    room = "A101"
                ),
                Schedule(
                    dayOfWeek = 5,
                    startPeriod = 4,
                    endPeriod = 6,
                    room = "B203"
                )
            )
        ),
        CourseClassEnrollmentData(
            capacity = 50,
            classCode = "CS202-02",
            className = "Nhóm 2",
            enrolledCount = 50,
            id = 2,
            lecturerCode = "GV002",
            lecturerName = "Trần Thị B",
            schedules = listOf(
                Schedule(
                    dayOfWeek = 3,
                    startPeriod = 7,
                    endPeriod = 9,
                    room = "C105"
                )
            )
        )
    )

    MaterialTheme {
        SchedulePickerDialog(
            courseTitle = "Cấu trúc dữ liệu & Giải thuật (CS202)",
            classGroups = mockClasses,
            isLoading = false,
            onSelect = {},
            onDismiss = {}
        )
    }
}