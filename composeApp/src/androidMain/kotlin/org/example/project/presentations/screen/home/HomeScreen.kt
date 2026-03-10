package org.example.project.presentations.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.presentations.screen.home.components.AlertList
import org.example.project.presentations.screen.home.components.FeatureList
import org.example.project.presentations.screen.home.components.HomeHeader
import org.example.project.presentations.screen.home.components.NewsAndEventsList
import org.example.project.presentations.screen.home.components.ScheduleClassList
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun HomeScreen(
    onOpenProfileScreen: () -> Unit = {},
    onOpenNotificationScreen: () -> Unit = {},
    onOpenFeatureScreen: () -> Unit,
    homeViewModel: HomeViewModel
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            HomeHeader(
                name = uiState.studentInfo?.fullName ?: "",
                studentCode = uiState.studentInfo?.studentCode ?: "",
                onOpenProfile = onOpenProfileScreen,
                onOpenNotification = onOpenNotificationScreen,
                isProfileReady = !uiState.loadingStudentInfo && uiState.studentInfo != null
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(bottom = 200.dp)
        ) {
            item(key = "alert_list", contentType = "AlertList") {
                AlertList(
                    items = uiState.alerts,
                    isLoading = uiState.loadingAlertList,
                    onClickAction = {},
                    modifier = Modifier.padding(top = 15.dp),
                )
            }

            item(key = "schedule_list", contentType = "ScheduleList") {
                ScheduleClassList(
                    isLoading = uiState.loadingScheduleClassList,
                    courseClasses = uiState.courseClasses,
                    modifier = Modifier.padding(horizontal = 15.dp)
                )
            }

            item(key = "feature_list", contentType = "FeatureList") {
                FeatureList(
                    modifier = Modifier.padding(top = 15.dp),
                    onClickItem = {},
                    onClickAll = onOpenFeatureScreen,
                    items = uiState.quickAccessList
                )
            }

            item(key = "news_events", contentType = "NewsEvents") {
                NewsAndEventsList(
                    items = uiState.newsAndEvents,
                    isLoading = uiState.loadingEventList,
                    modifier = Modifier.padding(top = 15.dp)
                )
            }
        }
    }
}