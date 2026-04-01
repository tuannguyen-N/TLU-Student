package org.example.project.presentations.screen.class_sign_up.components

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.LocationOn
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
import org.example.project.presentations.components.ButtonView
import org.example.project.presentations.theme.ExtendedColors
import org.example.project.presentations.theme.LocalExtendedColors


data class ScheduleSession(
    val dayLabel: String,      // e.g. "Thứ 2 (Tiết 1-3)"
    val room: String           // e.g. "Phòng B204"
)

data class ClassGroup(
    val groupName: String,         // e.g. "Lớp 1"
    val lecturerName: String,      // e.g. "TS. Nguyễn Văn A"
    val enrolled: Int,
    val capacity: Int,
    val sessions: List<ScheduleSession>
)

@Composable
fun SchedulePickerDialog(
    courseTitle: String = "Cấu trúc dữ liệu & Giải thuật (CS202)",
    classGroups: List<ClassGroup> = sampleClassGroups(),
    onSelect: (ClassGroup) -> Unit = {},
    onDismiss: () -> Unit = {}
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

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 600.dp)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(classGroups) { group ->
                        ClassGroupCard(
                            color = color,
                            onSelect = { onSelect(group) },
                            group = group
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ClassGroupCard(
    group: ClassGroup,
    color: ExtendedColors,
    onSelect: () -> Unit
) {
    val isFull = group.enrolled >= group.capacity

    val badgeBackground =
        if (isFull) color.gray.copy(alpha = 0.15f) else color.midBlue.copy(alpha = 0.15f)
    val badgeTextColor = if (isFull) color.gray else color.midBlue
    val badgeLabel = if (isFull) "Đã đầy:" else "Còn lại:"
    val badgeCountColor = if (isFull) color.red else color.midBlue

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        color = color.background,
        tonalElevation = 0.dp
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
                        text = group.groupName,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                        )
                    )
                    Text(
                        text = group.lecturerName,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.Gray,
                        )
                    )
                }

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = badgeBackground
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
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
                            text = "${group.enrolled}/${group.capacity}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = badgeCountColor,
                                fontWeight = FontWeight.Bold,
                            )
                        )
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                group.sessions.forEach { session ->
                    SessionItem(session = session, color = color)
                }
            }

            ButtonView(
                modifier = Modifier
                    .height(40.dp)
                    .width(200.dp),
                textSize = 14.sp,
                enabled = !isFull,
                text = "Chọn",
                backgroundColorRes = color.mainBlue,
                onClick = onSelect
            )
        }
    }
}

@Composable
fun SessionItem(
    session: ScheduleSession,
    color: ExtendedColors
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        color = color.white
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
                tint = color.midBlue,
                modifier = Modifier.size(18.dp)
            )
            Column {
                Text(
                    text = session.dayLabel,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.SemiBold,
                    )
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = color.red,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = session.room,
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

fun sampleClassGroups(): List<ClassGroup> {
    val sessions = listOf(
        ScheduleSession("Thứ 2 (Tiết 1-3)", "Phòng B204"),
        ScheduleSession("Thứ 2 (Tiết 1-3)", "Phòng B204"),
        ScheduleSession("Thứ 2 (Tiết 1-3)", "Phòng B204"),
    )
    return listOf(
        ClassGroup(
            groupName = "Lớp 1",
            lecturerName = "TS. Nguyễn Văn A",
            enrolled = 12,
            capacity = 45,
            sessions = sessions
        ),
        ClassGroup(
            groupName = "Lớp 1",
            lecturerName = "TS. Nguyễn Văn A",
            enrolled = 45,
            capacity = 45,
            sessions = sessions
        )
    )
}