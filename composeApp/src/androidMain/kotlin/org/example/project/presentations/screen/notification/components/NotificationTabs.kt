package org.example.project.presentations.screen.notification.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.NotificationSender
import org.example.project.domain.model.NotificationUiModel
import org.example.project.presentations.theme.ExtendedColors

@Composable
fun NotificationTabs(
    selectedTab: Int,
    color: ExtendedColors,
    unreadMap: Map<Int, Boolean>,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf("Tất cả", "Trường", "Giáo viên", "Khoa")

    PrimaryTabRow(
        selectedTabIndex = selectedTab,
        containerColor = color.white,
        contentColor = color.mainBlue,
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(selectedTab),
                color = color.mainBlue
            )
        }
    ) {
        tabs.forEachIndexed { index, title ->
            val hasUnread = unreadMap[index] == true
            Tab(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                selectedContentColor = color.mainBlue,
                unselectedContentColor = color.gray,
                text = {
                    Box {
                        Text(title)

                        if (hasUnread) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .align(Alignment.TopEnd)
                                    .offset(x = 4.dp, y = (-4).dp)
                                    .background(
                                        color.red,
                                        CircleShape
                                    )
                            )
                        }
                    }
                }
            )
        }
    }
}