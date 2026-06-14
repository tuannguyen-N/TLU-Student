package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.R
import org.example.project.domain.model.AlertUiModel
import org.example.project.domain.model.NotificationSeverity
import org.example.project.presentations.theme.ExtendedColors
import org.example.project.presentations.theme.LocalExtendedColors

private data class AlertCardStyle(
    val badgeText: String,
    val badgeBackground: Color,
    val badgeTextColor: Color,
    val actionBackground: Color,
    val accentColor: Color,
    val iconBackground: Color,
    val icon: Int? = null,
    val tagIcon: ImageVector? = null
)

@Composable
private fun ExtendedColors.styleFor(severity: NotificationSeverity): AlertCardStyle =
    when (severity) {
        NotificationSeverity.UPCOMING -> AlertCardStyle(
            badgeText = "Sắp diễn ra trong 7 ngày tới",
            badgeBackground = Color(0xFFFFF3E0),
            badgeTextColor = Color(0xFFF57C00),
            actionBackground = red,
            accentColor = Color(0xFFF57C00),
            iconBackground = Color(0xFFFFF3E0),
            icon = R.drawable.icon_upcoming,
            tagIcon = Icons.Default.CalendarMonth
        )

        NotificationSeverity.WARNING -> AlertCardStyle(
            badgeText = "Cần thực hiện ngay",
            badgeBackground = redLight,
            badgeTextColor = red,
            actionBackground = red,
            accentColor = red,
            iconBackground = redLight,
            icon = R.drawable.icon_caution,
            tagIcon = Icons.Default.ErrorOutline
        )

        else -> error("NORMAL không có card")
    }

@Composable
fun AlertCard(
    modifier: Modifier = Modifier,
    item: AlertUiModel,
    extraCount: Int = 0,
    cardWidth: Dp = Dp.Unspecified,
    onClickAction: () -> Unit = {}
) {
    val color = LocalExtendedColors.current
    val style = color.styleFor(item.severity)
    val totalCount = extraCount + 1

    Box(
        modifier = modifier
            .then(
                if (cardWidth != Dp.Unspecified) Modifier.width(cardWidth)
                else Modifier.fillMaxWidth()
            )
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = color.white),
            elevation = CardDefaults.cardElevation(3.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(6.dp)
                        .fillMaxHeight()
                        .background(
                            color = style.accentColor,
                            shape = RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)
                        )
                )

                Box(
                    modifier = Modifier
                        .padding(start = 14.dp, end = 4.dp, top = 16.dp, bottom = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(style.iconBackground),
                        contentAlignment = Alignment.Center
                    ) {
                        if (style.icon != null) {
                            Icon(
                                painter = painterResource(style.icon),
                                tint = style.accentColor,
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    if (totalCount > 1) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(color.red),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "$totalCount",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = color.white,
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 14.dp)
                        .padding(end = 8.dp, start = 5.dp),
                ) {
                    Text(
                        text = "Thông báo nhắc nhở",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                        color = color.blackBackground,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = buildAnnotatedString {
                            append("Bạn có ")
                            withStyle(
                                SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = style.accentColor
                                )
                            ) {
                                append("$totalCount")
                            }
                            append(if (item.severity == NotificationSeverity.UPCOMING) " việc cần làm sắp tới" else " việc cần làm trong hôm nay")
                        },
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall,
                        color = color.gray
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50.dp))
                            .background(style.badgeBackground)
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        if (style.tagIcon != null) {
                            Icon(
                                imageVector = style.tagIcon,
                                contentDescription = null,
                                tint = style.badgeTextColor,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                        Text(
                            text = style.badgeText,
                            style = MaterialTheme.typography.labelSmall,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = style.badgeTextColor
                        )
                    }
                }

                Box(
                    modifier = Modifier.padding(end = 14.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50.dp))
                            .background(style.actionBackground)
                            .clickable { onClickAction() }
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Xem chi tiết",
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.labelSmall,
                            color = color.white
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                            contentDescription = null,
                            tint = color.white,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                }
            }
        }
    }
}