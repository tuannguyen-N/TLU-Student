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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import org.example.project.R
import org.example.project.presentations.components.ButtonView
import org.example.project.presentations.components.LabelView
import org.example.project.presentations.theme.ExtendedColors
import org.example.project.presentations.theme.LocalExtendedColors

data class ClassSession(
    val dayLabel: String,   // e.g. "Thứ 2 (Tiết 1-3)"
    val room: String        // e.g. "Phòng B204"
)

data class ClassGroup(
    val groupName: String,          // e.g. "Lớp 1"
    val lecturer: String,           // e.g. "TS. Nguyễn Văn A"
    val enrolled: Int,
    val capacity: Int,
    val sessions: List<ClassSession>
) {
    val isFull get() = enrolled >= capacity
    val remaining get() = capacity - enrolled
}

// ── Dialog ───────────────────────────────────────────────────────────────────

@Preview(showBackground = true)
@Composable
fun SelectClassesDialog(
    courseTitle: String = "Cấu trúc dữ liệu & Giải thuật (CS202)",
    groups: List<ClassGroup> = sampleGroups(),
    onDismiss: () -> Unit = {},
    onSelectGroup: (ClassGroup) -> Unit = {}
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
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = Color.White,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp, horizontal = 18.dp)
            ) {
                TitleDialog(
                    courseTitle = courseTitle,
                    color = color,
                    onDismiss = onDismiss
                )
                Spacer(Modifier.height(8.dp))

                HorizontalDivider(color = color.gray.copy(alpha = 0.15f))

                Spacer(Modifier.height(16.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.heightIn(max = 560.dp)
                ) {
                    items(groups) { group ->
                        ClassGroupCard(
                            group = group,
                            color = color,
                            onSelectGroup = { onSelectGroup(group) }
                        )
                    }
                    item { Spacer(Modifier.height(4.dp)) }
                }
            }
        }
    }
}

// ── Title ─────────────────────────────────────────────────────────────────────

@Composable
private fun TitleDialog(
    modifier: Modifier = Modifier,
    courseTitle: String,
    color: ExtendedColors,
    onDismiss: () -> Unit
) {
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Chọn nhóm học",
                color = color.fontBlue,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = onDismiss,
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Đóng",
                    tint = color.gray
                )
            }
        }
        Text(
            text = courseTitle,
            color = color.mainBlue,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Normal
        )
    }
}

@Composable
private fun ClassGroupCard(
    group: ClassGroup,
    color: ExtendedColors,
    onSelectGroup: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (group.isFull)
                color.background // TODO:
            else
                color.cardBackground
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Top) {
                    Text(
                        text = group.groupName,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = group.lecturer,
                        style = MaterialTheme.typography.bodySmall,
                        color = color.grayNavy
                    )
                }

                LabelView(
                    text = if (group.isFull) "Đã đầy" else "Còn lại: ", // TODO:
                    backgroundColor = color.gray.copy(alpha = 0.1f),
                    textColor = color.gray
                )
            }

            group.sessions.forEach { session ->
                SessionRow(session = session, color = color)
            }

            ButtonView(
                text = "Chọn nhóm",
                modifier = Modifier.height(40.dp),
                textSize = 13.sp,
                backgroundColorRes = color.mainBlue,
                textColorRes = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
        }
    }
}

@Composable
private fun SessionRow(
    session: ClassSession,
    color: ExtendedColors
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_calender),
                contentDescription = null,
                tint = color.fontBlue,
            )
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    text = session.dayLabel,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = color.gray,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = session.room,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Normal,
                        color = color.gray
                    )
                }
            }
        }
    }
}

private fun sampleGroups() = listOf(
    ClassGroup(
        groupName = "Lớp 1",
        lecturer  = "TS. Nguyễn Văn A",
        enrolled  = 33,
        capacity  = 45,
        sessions  = listOf(
            ClassSession("Thứ 2 (Tiết 1-3)", "Phòng B204"),
            ClassSession("Thứ 2 (Tiết 1-3)", "Phòng B204"),
            ClassSession("Thứ 2 (Tiết 1-3)", "Phòng B204"),
        )
    ),
    ClassGroup(
        groupName = "Lớp 1",
        lecturer  = "TS. Nguyễn Văn A",
        enrolled  = 45,
        capacity  = 45,
        sessions  = listOf(
            ClassSession("Thứ 2 (Tiết 1-3)", "Phòng B204"),
            ClassSession("Thứ 2 (Tiết 1-3)", "Phòng B204"),
            ClassSession("Thứ 2 (Tiết 1-3)", "Phòng B204"),
        )
    )
)