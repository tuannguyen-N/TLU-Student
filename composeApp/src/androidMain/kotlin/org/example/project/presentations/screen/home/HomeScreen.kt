package org.example.project.presentations.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.domain.model.FeatureType
import org.example.project.presentations.screen.home.components.AlertList
import org.example.project.presentations.screen.home.components.EmptyAlertCard
import org.example.project.presentations.screen.home.components.FeatureList
import org.example.project.presentations.screen.home.components.HomeHeader
import org.example.project.presentations.screen.home.components.NewsAndEventsList
import org.example.project.presentations.screen.home.components.QuoteCard
import org.example.project.presentations.screen.home.components.ScheduleClassList
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    onOpenProfileScreen: () -> Unit = {},
    onOpenNotificationScreen: () -> Unit = {},
    onOpenFeature: (FeatureType) -> Unit,
    onOpenFeatureScreen: () -> Unit,
    onOpenScheduleScreen: () -> Unit,
    onOpenNewsScreen: () -> Unit,
    onOpenNews: (String) -> Unit,
    onOpenChat: () -> Unit,
    openAlertsAndActionsScreen: () -> Unit,
    onOpenSearch: () -> Unit
) {
    val color = LocalExtendedColors.current
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullToRefreshState()

    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            HomeHeader(
                name = uiState.studentInfo?.fullName ?: "",
                studentCode = uiState.studentInfo?.studentCode ?: "",
                onOpenProfile = onOpenProfileScreen,
                onOpenNotification = onOpenNotificationScreen,
                isProfileReady = !uiState.loadingStudentInfo && uiState.studentInfo != null,
                avatarUrl = uiState.studentInfo?.avatarUrl,
                onOpenChat = onOpenChat,
                isNotificationBadgeVisible = !uiState.isAllNotificationsRead,
                onOpenSearch = onOpenSearch
            )
        }
    ) { paddingValues ->
        PullToRefreshBox(
            isRefreshing = uiState.isRefreshing,
            onRefresh = {
                homeViewModel.onAction(HomeAction.RefreshData)
            },
            state = pullRefreshState,
            modifier = Modifier.padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                contentPadding = PaddingValues(bottom = 200.dp)
            ) {
                item(key = "alert_list", contentType = "AlertList") {
                    if (uiState.alerts.isNotEmpty()) {
                        AlertList(
                            items = uiState.alerts,
                            isLoading = uiState.loadingAlertList,
                            onClickAction = openAlertsAndActionsScreen,
                            modifier = Modifier.padding(top = 10.dp),
                        )
                    } else {
                        EmptyAlertCard(
                            modifier = Modifier.padding(
                                start = 20.dp,
                                end = 20.dp,
                                top = 10.dp
                            )
                        )
                    }
                }

                item(key = "schedule_list", contentType = "ScheduleList") {
                    ScheduleClassList(
                        isLoading = uiState.loadingScheduleClassList,
                        modifier = Modifier.padding(horizontal = 20.dp),
                        onClickViewTomorrow = onOpenScheduleScreen,
                        color = color,
                        daySchedule = uiState.upcomingSchedules
                    )
                }

                item(key = "feature_list", contentType = "FeatureList") {
                    FeatureList(
                        color = color,
                        items = uiState.quickAccessList,
                        onClickItem = onOpenFeature,
                        onClickAll = onOpenFeatureScreen
                    )
                }

                item(key = "news_events", contentType = "NewsEvents") {
                    NewsAndEventsList(
                        items = uiState.newsAndEvents,
                        isLoading = uiState.loadingEventList,
                        modifier = Modifier.padding(top = 15.dp),
                        onClickAll = onOpenNewsScreen,
                        onOpenNews = onOpenNews
                    )
                }

                item(key = "advices") {
                    QuoteCard(dailyQuote = uiState.dailyQuote)
                }
            }
        }
    }
}