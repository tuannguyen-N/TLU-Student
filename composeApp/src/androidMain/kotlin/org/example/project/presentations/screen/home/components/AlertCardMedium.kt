package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.AlertUiModel
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun MediumAlertCard(
    modifier: Modifier = Modifier,
    item: AlertUiModel,
    onClickAction: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .padding(start = 10.dp)
            .width(235.dp)
            .height(180.dp)
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = LocalExtendedColors.current.white
            ),
            elevation = CardDefaults.cardElevation(3.dp),
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Column(
                modifier = Modifier
                    .padding(13.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .clip(RoundedCornerShape(5.dp))
                        .background(LocalExtendedColors.current.yellowLight)
                        .padding(horizontal = 6.dp, vertical = 3.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Sắp đến hạn",
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.bodySmall,
                        color = LocalExtendedColors.current.yellow
                    )
                }

                Text(
                    text = item.title,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleMedium,
                    color = LocalExtendedColors.current.mainBlue
                )

                Text(
                    text = item.content,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Normal,
                    style = MaterialTheme.typography.bodySmall,
                    color = LocalExtendedColors.current.mainBlue,
                    modifier = Modifier.padding(top = 8.dp, bottom = 15.dp)
                )

                Text(
                    text = "Hành động ngay",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyMedium,
                    color = LocalExtendedColors.current.white,
                    modifier = Modifier
                        .clickable {
                            onClickAction()
                        }
                        .clip(RoundedCornerShape(6.dp))
                        .background(LocalExtendedColors.current.yellow)
                        .padding(horizontal = 32.dp, vertical = 8.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}