package org.example.project.presentations.screen.student_class.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.data.remote.dto.student_class.Student
import org.example.project.presentations.theme.ExtendedColors
import org.example.project.presentations.theme.LocalExtendedColors

enum class StudentRole(val label: String) {
    CLASS_PRESIDENT("LỚP TRƯỞNG"),
    CLASS_VICE("LỚP PHÓ"),
    SECRETARY("BÍ THƯ"),
    NONE("")
}

@Composable
private fun roleBadgeColors(role: StudentRole):  Color = when (role) {
    StudentRole.CLASS_PRESIDENT -> LocalExtendedColors.current.red
    StudentRole.CLASS_VICE -> LocalExtendedColors.current.purple
    StudentRole.SECRETARY -> LocalExtendedColors.current.lightBlue
    StudentRole.NONE -> Color.Transparent
}

@Composable
fun StudentItem(
    student: Student,
    role: StudentRole = StudentRole.NONE,
    color: ExtendedColors = LocalExtendedColors.current,
) {
    val avatarLetter = student.fullName
        .trim()
        .split(" ")
        .lastOrNull { it.isNotBlank() }
        ?.firstOrNull()
        ?.uppercaseChar()
        ?.toString() ?: "?"

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(width = 2.dp, color = color.gray.copy(alpha = 0.1f)),
        colors = CardDefaults.cardColors(color.white),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(color.midBlue.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = avatarLetter,
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = color.midBlue,
                        fontWeight = FontWeight.SemiBold,
                    ),
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = student.fullName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                        )
                    )

                    if (role != StudentRole.NONE) {
                        val color = roleBadgeColors(role)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(color.copy(alpha = 0.1f))
                                .padding(horizontal = 8.dp, vertical = 3.dp),
                        ) {
                            Text(
                                text = role.label,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = color,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp,
                                ),
                            )
                        }
                    }
                }

                Text(
                    text = "MSSV: ${student.studentCode}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Normal,
                    )
                )
            }
        }
    }
}