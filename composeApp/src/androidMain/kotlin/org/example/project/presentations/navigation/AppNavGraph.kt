package org.example.project.presentations.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.project.presentations.screen.edit_profile.EditProfileScreen
import org.example.project.presentations.screen.login.LoginScreen
import org.example.project.presentations.screen.main.MainScreen
import org.example.project.presentations.screen.notification.NotificationScreen
import org.example.project.presentations.screen.transcript_term.TranscriptTermScreen
import org.example.project.presentations.screen.profile.ProfileScreen
import org.example.project.presentations.screen.setting.SettingScreen
import org.example.project.presentations.screen.splash.SplashScreen
import org.example.project.presentations.screen.timetable.TimetableScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Splash
    ) {
        composable(Routes.Splash) {
            SplashScreen(navController)
        }

        composable(Routes.Login){
            LoginScreen()
        }

        composable(Routes.Main) {
            MainScreen(
                onOpenProfileScreen = {
                    navController.navigate(Routes.Profile)
                },
                onOpenNotificationScreen = {
                    navController.navigate(Routes.Notification)
                },
                onOpenTranscriptTerm = {
                    navController.navigate(Routes.TranscriptTerm)
                },
                onOpenTimetable = {
                    navController.navigate(Routes.TimetableScreen)
                }
            )
        }

        composable(Routes.Profile) {
            ProfileScreen(
                rootNavController = navController,
                onOpenSetting = {
                    navController.navigate(Routes.Setting)
                },
                onOpenEditProfile = {
                    navController.navigate(Routes.EditProfile)
                }
            )
        }

        composable(Routes.Setting) {
            SettingScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.EditProfile) {
            EditProfileScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.Notification) {
            NotificationScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.TranscriptTerm) {
            TranscriptTermScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.TimetableScreen) {
            TimetableScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}