package org.example.project.presentations.screen.class_sign_up.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.CourseFilter
import org.example.project.domain.model.CourseItem
import org.example.project.domain.model.CourseStatus
import org.example.project.presentations.components.LabelView
import org.example.project.presentations.theme.LocalExtendedColors
import org.example.project.presentations.theme.MainBlue

@Preview(showBackground = true)
@Composable
fun CourseCard(
    course: CourseItem = CourseItem(
        "EN101",
        2,
        "Tiếng Anh 1",
        CourseStatus.FULL,
        CourseFilter.ELECTIVE
    ),
    mainBlue: Color = LocalExtendedColors.current.mainBlue,
    gray: Color = LocalExtendedColors.current.gray,
    white: Color = Color.White
) {
    val isFull = course.status == CourseStatus.FULL

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                if (isFull) {
                    val strokeWidth = 1.dp.toPx()
                    val cornerRadius = 14.dp.toPx()
                    drawRoundRect(
                        color = Color.Gray,
                        size = size,
                        cornerRadius = CornerRadius(cornerRadius),
                        style = Stroke(
                            width = strokeWidth,
                            pathEffect = PathEffect.dashPathEffect(
                                floatArrayOf(10f, 10f), 0f
                            )
                        )
                    )
                }
            },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (isFull) Color.Transparent else white
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = if (isFull) 0.dp else 1.dp
        ),
    ) {
        Column(modifier = Modifier.padding(14.dp)) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${course.code} • ${course.credits} TÍN CHỈ",
                    style = MaterialTheme.typography.labelMedium,
                    color = gray,
                    modifier = Modifier.weight(1f)
                )

                if (isFull) {
                    LabelView(
                        text = "Đã đầy",
                        backgroundColor = gray.copy(alpha = 0.1f),
                        textColor = gray
                    )
                }
            }

            Spacer(Modifier.height(2.dp))

            Text(
                text = course.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (isFull) gray else Color.Black,
            )

            if (!isFull) {
                Spacer(Modifier.height(6.dp))
                Button(
                    onClick = {
                        // TODO:
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = mainBlue,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(30.dp)
                ) {
                    Text(
                        text = "Đăng ký",
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
