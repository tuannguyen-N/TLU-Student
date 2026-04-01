package org.example.project.presentations.screen.student_class.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.ContentCopy
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.example.project.presentations.components.LabelHeader
import org.example.project.presentations.theme.ExtendedColors


@Composable
fun TeacherCard(
    modifier: Modifier = Modifier,
    color: ExtendedColors,
    teacherName: String,
    teacherId: String,
    email: String,
    phone: String,
    onCopy: (String) -> Unit,
) {
    val avatarLetter = teacherName
        .split(" ")
        .lastOrNull { it.isNotBlank() }
        ?.firstOrNull()
        ?.uppercaseChar()
        ?.toString() ?: "?"

    Column {
        LabelHeader("Thông tin cố vấn học tập")

        Card(
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(color.white),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
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
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = color.midBlue,
                            fontWeight = FontWeight.SemiBold,
                        ),
                    )
                }

                Column {
                    Text(
                        text = teacherName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                        ),
                    )

                    Spacer(Modifier.height(4.dp))

                    InfoRow(
                        icon = Icons.Outlined.Code,
                        value = teacherId,
                        color = color,
                        onCopy = onCopy
                    )
                    InfoRow(
                        icon = Icons.Outlined.Email,
                        value = email,
                        color = color,
                        onCopy = onCopy
                    )
                    InfoRow(
                        icon = Icons.Outlined.Phone,
                        value = phone,
                        color = color,
                        onCopy = onCopy
                    )
                }
            }
        }
    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
    value: String,
    onCopy: (String) -> Unit,
    color: ExtendedColors
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color.gray,
            modifier = Modifier.size(18.dp),
        )

        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = color.gray,
            ),
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        IconButton(
            onClick = {
                onCopy(value)
            },
            modifier = Modifier.size(20.dp),
        ) {
            Icon(
                imageVector = Icons.Outlined.ContentCopy,
                contentDescription = "Copy $value",
                tint = color.gray,
                modifier = Modifier.size(14.dp),
            )
        }
    }
}