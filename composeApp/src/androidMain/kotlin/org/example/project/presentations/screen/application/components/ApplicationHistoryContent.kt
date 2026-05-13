package org.example.project.presentations.screen.application.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.ApplicationStatus
import org.example.project.domain.model.FeedbackItem
import org.example.project.presentations.theme.LocalExtendedColors

import org.example.project.data.remote.dto.application_history.ApplicationHistoryData

@Preview(showBackground = true)
@Composable
fun ApplicationHistoryContent(
    applications: List<ApplicationHistoryData> = emptyList(),
    onCreateFeedback: () -> Unit = {},
    onViewDetail: (String) -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (applications.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Chưa có lịch sử phản hồi",
                    color = LocalExtendedColors.current.gray,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 8.dp, bottom = 88.dp)
            ) {
                items(applications) { item ->
                    ApplicationHistoryCard(
                        item = item,
                        onViewDetail = onViewDetail
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = onCreateFeedback,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = LocalExtendedColors.current.mainRed,
            contentColor = Color.White,
            shape = CircleShape
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Tạo phản hồi"
            )
        }
    }
}
