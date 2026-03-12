package org.example.project.presentations.screen.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.example.project.presentations.screen.BottomBar
import org.example.project.presentations.screen.home.HomeScreen
import org.example.project.presentations.screen.home.HomeViewModel
import org.example.project.presentations.screen.school_schedule.ScheduleScreen
import org.example.project.presentations.screen.school_schedule.ScheduleViewModel
import org.example.project.presentations.screen.transcript.TranscriptScreen
import org.example.project.presentations.screen.transcript.TranscriptViewModel
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun MainScreen(
    scheduleViewModel: ScheduleViewModel,
    transcriptViewModel: TranscriptViewModel,
    homeViewModel: HomeViewModel,
    onOpenProfileScreen: () -> Unit,
    onOpenNotificationScreen: () -> Unit,
    onOpenTranscriptTerm: (String, String) -> Unit,
    onOpenTimetable: () -> Unit,
    onOpenFeatureScreen: () -> Unit,
    onSendEmail: (String) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 4 })
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            containerColor = LocalExtendedColors.current.background,
            contentWindowInsets = WindowInsets(0)
        ) { paddingValues ->

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                userScrollEnabled = false
            ) { page ->
                when (page) {
                    0 -> HomeScreen(
                        homeViewModel = homeViewModel,
                        onOpenProfileScreen = onOpenProfileScreen,
                        onOpenNotificationScreen = onOpenNotificationScreen,
                        onOpenFeatureScreen = onOpenFeatureScreen
                    )

                    1 -> ScheduleScreen(
                        onOpenNotificationScreen = onOpenNotificationScreen,
                        onOpenTimetable = onOpenTimetable,
                        viewModel = scheduleViewModel,
                        onSendEmail = { onSendEmail(it) }
                    )

                    2 -> Box(modifier = Modifier.fillMaxSize())
                    3 -> TranscriptScreen(
                        viewModel = transcriptViewModel,
                        onOpenNotificationScreen = onOpenNotificationScreen,
                        onOpenTranscriptTerm = { semester -> 
                            onOpenTranscriptTerm(semester.semesterLabel, semester.academicYear) 
                        }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(bottom = 10.dp)
        ) {
            BottomBar(
                currentPage = pagerState.currentPage,
                onTabSelected = { index ->
                    coroutineScope.launch {
                        pagerState.scrollToPage(index)
                    }
                }
            )
        }
    }
}