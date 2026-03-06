package org.example.project.presentations.screen.main

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.project.presentations.navigation.BottomRoutes
import org.example.project.presentations.screen.BottomBar
import org.example.project.presentations.screen.home.HomeScreen
import org.example.project.presentations.screen.home.HomeViewModel
import org.example.project.presentations.screen.school_schedule.ScheduleScreen
import org.example.project.presentations.screen.school_schedule.ScheduleViewModel
import org.example.project.presentations.screen.transcript.TranscriptScreen
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun MainScreen(
    onOpenProfileScreen: () -> Unit,
    onOpenNotificationScreen: () -> Unit,
    onOpenTranscriptTerm: () -> Unit,
    onOpenTimetable: () -> Unit,
    homeViewModel: HomeViewModel,
    scheduleViewModel: ScheduleViewModel
) {
    val navController = rememberNavController()

    Box(modifier = Modifier.fillMaxSize()) {

        Scaffold(
            containerColor = LocalExtendedColors.current.background,
            contentWindowInsets = WindowInsets(0)
        ) { paddingValues ->

            NavHost(
                navController = navController,
                startDestination = BottomRoutes.Home,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(
                    BottomRoutes.Home,
                    enterTransition = { fadeIn(tween(300)) },
                    exitTransition = { fadeOut(tween(300)) },
                ) {
                    HomeScreen(
                        onOpenProfileScreen = {
                            onOpenProfileScreen()
                        },
                        onOpenNotificationScreen = onOpenNotificationScreen,
                        homeViewModel = homeViewModel,

                    )
                }

                composable(
                    BottomRoutes.SchoolSchedule,
                    enterTransition = { fadeIn(tween(300)) },
                    exitTransition = { fadeOut(tween(300)) },
                ) {
                    ScheduleScreen(
                        onOpenNotificationScreen = onOpenNotificationScreen,
                        onOpenTimetable = onOpenTimetable,
                        viewModel = scheduleViewModel
                    )
                }

                composable(
                    BottomRoutes.Chat, enterTransition = { fadeIn(tween(300)) },
                    exitTransition = { fadeOut(tween(300)) },
                ) {

                }

                composable(
                    BottomRoutes.Transcript, enterTransition = { fadeIn(tween(300)) },
                    exitTransition = { fadeOut(tween(300)) },
                ) {
                    TranscriptScreen(
                        onOpenNotificationScreen = onOpenNotificationScreen,
                        onOpenTranscriptTerm = onOpenTranscriptTerm
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
            BottomBar(navController)
        }
    }
}