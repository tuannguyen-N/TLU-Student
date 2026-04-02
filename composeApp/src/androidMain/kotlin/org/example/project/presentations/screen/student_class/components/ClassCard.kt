package org.example.project.presentations.screen.student_class.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.R
import org.example.project.presentations.theme.ExtendedColors

@Composable
fun ClassCard(
    modifier: Modifier = Modifier,
    className: String ,
    major: String,
    color: ExtendedColors
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(170.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Box {
            Image(
                painter = painterResource(R.drawable.bg_digital_card_student),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(
                        radiusX = 5.dp,
                        radiusY = 5.dp,
                        edgeTreatment = BlurredEdgeTreatment.Rectangle,
                    )
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(24.dp)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(color.white.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Filled.School,
                    contentDescription = "School icon",
                    tint = color.white,
                    modifier = Modifier.size(24.dp),
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(24.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = "Tên lớp",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = color.white.copy(alpha = 0.80f),
                            letterSpacing = 2.sp,
                            fontWeight = FontWeight.Medium,
                        ),
                    )
                    Text(
                        text = className,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            color = color.white,
                            fontWeight = FontWeight.ExtraBold,
                        ),
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = "Chuyên ngành",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = color.white.copy(alpha = 0.85f),
                            fontWeight = FontWeight.Normal,
                        ),
                    )
                    Text(
                        text = major,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = color.white,
                            fontWeight = FontWeight.SemiBold,
                        ),
                    )
                }
            }
        }
    }
}