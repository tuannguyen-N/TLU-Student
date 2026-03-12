package org.example.project.presentations.screen.school_schedule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import org.example.project.presentations.theme.LocalExtendedColors

@Preview
@Composable
fun LecturerCard(
    lecturerName: String = "ThS. Nguyễn Văn Tây",
    lecturerId: String = "GV001",
    onOpenDetailLecturerInfo: () -> Unit = {}
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F7)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(12.dp), verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(45.dp)
                        .background(
                            color = LocalExtendedColors.current.mainBlue,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(25.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = "GIẢNG VIÊN",
                        style = MaterialTheme.typography.labelSmall,
                        color = LocalExtendedColors.current.gray,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.3.sp
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = lecturerName,
                        style = MaterialTheme.typography.titleLarge,
                        color = LocalExtendedColors.current.mainBlue,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color.Blue.copy(alpha = 0.12f),
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = lecturerId,
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Blue,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = onOpenDetailLecturerInfo,
                    modifier = Modifier.size(18.dp).align(Alignment.Top)
                ) {
                    Icon(
                        painter = painterResource(org.example.project.R.drawable.icon_more_information),
                        contentDescription = null,
                        tint = LocalExtendedColors.current.mainBlue,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}