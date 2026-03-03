package org.example.project.presentations.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.R
import org.example.project.presentations.theme.LocalExtendedColors

@Preview
@Composable
fun TopScreenBar(
    onBack: () -> Unit = {},
    title: String = "Chi tiết học kỳ 1"
) {
    var showTermYear by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .background(LocalExtendedColors.current.mainRed)
            .statusBarsPadding(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_back),
                    contentDescription = null,
                    tint = Color.White
                )
            }

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.weight(1f))

            Box {
                TermYear(
                    academicYear = "2023-2024",
                    onClick = { showTermYear = true },
                    modifier = Modifier.padding(end = 12.dp)
                )

                if (showTermYear) {
                    TermYearMenuView(
                        onDismiss = { showTermYear = false }
                    )
                }
            }
        }
    }
}

@Composable
fun TermYear(
    academicYear: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        color = Color(0x4DFFFFFF),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(
            0.7.dp,
            color = Color(0x1AFFFFFF)
        ),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(vertical = 3.dp, horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = academicYear,
                color = Color.White,
                style = MaterialTheme.typography.titleSmall,
            )

            Icon(
                painter = painterResource(R.drawable.icon_down),
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}