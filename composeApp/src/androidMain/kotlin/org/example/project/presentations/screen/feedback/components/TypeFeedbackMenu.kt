package org.example.project.presentations.screen.feedback.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import org.example.project.domain.model.SubjectOption

@Preview
@Composable
fun TypeFeedbackMenu(
    onSelected: (SubjectOption) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val subjectOptions = SubjectOption.entries

    Popup(
        alignment = Alignment.TopEnd,
        onDismissRequest = onDismiss
    ) {
        Card(
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier
                    .width(200.dp)
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(subjectOptions) { item ->
                    Surface(
                        onClick = {
                            onSelected(item)
                            onDismiss()
                        },
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = item.value,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 13.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    }

                    if (item != subjectOptions.last()) {
                        Spacer(
                            Modifier
                                .fillMaxWidth()
                                .height(0.5.dp)
                                .background(Color.Gray)
                        )
                    }
                }
            }
        }
    }
}