package org.example.project.presentations.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.project.presentations.screen.SharedViewModel
import org.example.project.presentations.screen.edit_profile.EditProfileScreen
import org.example.project.presentations.screen.edit_profile.EditProfileViewModel
import org.example.project.presentations.screen.edit_profile.EditProfileViewModelFactory
import org.example.project.presentations.screen.feedback.ScreenView
import org.example.project.presentations.screen.feedback_detail.FeedbackDetailScreen
import org.example.project.presentations.screen.home.HomeViewModel
import org.example.project.presentations.screen.home.HomeViewModelFactory
import org.example.project.presentations.screen.login.LoginScreen
import org.example.project.presentations.screen.login.LoginViewModel
import org.example.project.presentations.screen.login.LoginViewModelFactory
import org.example.project.presentations.screen.main.LocalAppContainer
import org.example.project.presentations.screen.main.MainScreen
import org.example.project.presentations.screen.notification.NotificationScreen
import org.example.project.presentations.screen.profile.ProfileScreen
import org.example.project.presentations.screen.profile.ProfileViewModel
import org.example.project.presentations.screen.profile.ProfileViewModelFactory
import org.example.project.presentations.screen.school_schedule.ScheduleViewModel
import org.example.project.presentations.screen.school_schedule.ScheduleViewModelFactory
import org.example.project.presentations.screen.setting.SettingScreen
import org.example.project.presentations.screen.splash.SplashScreen
import org.example.project.presentations.screen.timetable.TimetableScreen
import org.example.project.presentations.screen.transcript_term.TranscriptTermScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.Splash
    ) {
        composable(Routes.Splash) {
            SplashScreen(navController)
        }

        composable(Routes.Login) {
            val container = LocalAppContainer.current
            val loginViewModel: LoginViewModel = viewModel(
                factory = LoginViewModelFactory(container.loginUseCase)
            )

            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(Routes.Main)
                },
                loginViewModel = loginViewModel
            )
        }

        composable(Routes.Main) {
            val container = LocalAppContainer.current
            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModelFactory(container.studentUseCase, container.scheduleUseCase)
            )
            val scheduleViewModel: ScheduleViewModel = viewModel(
                factory = ScheduleViewModelFactory(container.scheduleUseCase)
            )

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
                },
                homeViewModel = homeViewModel,
                scheduleViewModel = scheduleViewModel
            )
        }

        composable(Routes.Profile) {
            val container = LocalAppContainer.current

            val profileViewModel: ProfileViewModel = viewModel(
                factory = ProfileViewModelFactory(container.studentUseCase)
            )

            ProfileScreen(
                onBack = { navController.popBackStack() },
                onOpenSetting = { navController.navigate(Routes.Setting) },
                onOpenEditProfile = { navController.navigate(Routes.EditProfile) },
                viewModel = profileViewModel,
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
            val container = LocalAppContainer.current
            val editProfileViewModel: EditProfileViewModel = viewModel(
                factory = EditProfileViewModelFactory(container.studentUseCase)
            )

            EditProfileScreen(
                viewModel = editProfileViewModel,
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

        composable(Routes.Feedback) {
            ScreenView(
                viewModel = viewModel(), // TODO:
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.FeedbackDetail) {
            FeedbackDetailScreen(
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}