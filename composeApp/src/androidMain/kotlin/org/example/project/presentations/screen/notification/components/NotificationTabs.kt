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
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun NotificationTabs(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf("Tất cả", "Trường", "Giáo viên", "Khoa")

    PrimaryTabRow(
        selectedTabIndex = selectedTab,
        containerColor = LocalExtendedColors.current.white,
        contentColor = LocalExtendedColors.current.mainBlue,
        indicator = {
            TabRowDefaults.PrimaryIndicator(
                modifier = Modifier.tabIndicatorOffset(selectedTab),
                color = LocalExtendedColors.current.mainBlue
            )
        }
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                selectedContentColor = LocalExtendedColors.current.mainBlue,
                unselectedContentColor = LocalExtendedColors.current.gray,
                text = {
                    Box {
                        Text(title)
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .align(Alignment.TopEnd)
                                .offset(x = 4.dp, y = (-4).dp)
                                .background(LocalExtendedColors.current.red, CircleShape)
                        )
                    }
                }
            )
        }
    }
}