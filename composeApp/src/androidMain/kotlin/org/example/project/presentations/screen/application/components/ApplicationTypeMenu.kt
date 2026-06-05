package org.example.project.presentations.screen.application.components

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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import org.example.project.data.remote.dto.application.ApplicationType

@Composable
fun ApplicationTypeMenu(
    applicationTypes: List<ApplicationType>,
    onSelected: (ApplicationType) -> Unit = {},
    onDismiss: () -> Unit = {}
) {

    Popup(
        alignment = Alignment.TopStart,
        onDismissRequest = onDismiss,
        offset = IntOffset(0,130)
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
                items(applicationTypes) { item ->
                    Surface(
                        onClick = {
                            onSelected(item)
                            onDismiss()
                        },
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = item.name,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 13.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Black,
                            textAlign = TextAlign.Center
                        )
                    }

                    if (item != applicationTypes.last()) {
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