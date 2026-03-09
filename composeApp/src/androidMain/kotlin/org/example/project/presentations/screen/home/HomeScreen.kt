package org.example.project.presentations.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.example.project.domain.model.AlertUiModel
import org.example.project.domain.model.FeatureUiModel
import org.example.project.domain.model.NewAndEventUiModel
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
    homeViewModel: HomeViewModel
) {
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    val alerts = remember { AlertUiModel.getDemoList() }
    val newAndEvent = remember { NewAndEventUiModel.getDataDemo() }
    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            HomeHeader(
                name = uiState.studentInfo?.fullName ?: "",
                studentCode = uiState.studentInfo?.fullName ?: "",
                onOpenProfile = { onOpenProfileScreen() },
                onOpenNotification = onOpenNotificationScreen,
                isProfileReady = !uiState.loadingStudentInfo && uiState.studentInfo != null
            )
        }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
                AlertList(
                    items = alerts,
                    isLoading = uiState.loadingAlertList,
                    onClickAction = {},
                    modifier = Modifier.padding(top = 15.dp),
                )
                ScheduleClassList(
                    onClickAll = {
                        // TODO:
                    },
                    isLoading = uiState.loadingScheduleClassList,
                    courseClasses = uiState.courseClasses,
                    modifier = Modifier.padding(horizontal = 15.dp)
                )

                FeatureList(
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .padding(top = 15.dp)
                )

                NewsAndEventsList(
                    items = newAndEvent,
                    isLoading = uiState.loadingEventList,
                    modifier = Modifier.padding(top = 15.dp)
                )
                Spacer(modifier = Modifier.height(200.dp))
            }
        }
    }
}