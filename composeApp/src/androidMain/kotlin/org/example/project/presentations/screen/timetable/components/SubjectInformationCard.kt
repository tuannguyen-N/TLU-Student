package org.example.project.presentations.screen.timetable.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.presentations.theme.LocalExtendedColors

@Preview(showBackground = true)
@Composable
fun SubjectInformationCard(
    modifier: Modifier = Modifier,
    room: String = "Phòng 101",
    subjectName: String = "Lập trình di động",
    onShowSubjectDetail: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .padding(1.dp)
            .fillMaxSize()
            .background(Color(0xFFFFEBE9))
            .padding(5.dp)
    ) {
        Row(
        ) {
            Text(
                color = LocalExtendedColors.current.red,
                text = room,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = onShowSubjectDetail,
                modifier = Modifier.size(14.dp)
            ) {
                Icon(
                    painter = painterResource(org.example.project.R.drawable.icon_more_information),
                    contentDescription = null,
                    tint = Color.Black,
                    modifier = Modifier.size(13.dp)
                )
            }
        }

        Text(
            text = subjectName,
            color = LocalExtendedColors.current.mainBlue,
            style = MaterialTheme.typography.labelMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )
    }
}