package org.example.project.presentations.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.project.domain.model.FeatureType
import org.example.project.presentations.screen.SharedViewModel
import org.example.project.presentations.screen.edit_profile.EditProfileScreen
import org.example.project.presentations.screen.edit_profile.EditProfileViewModel
import org.example.project.presentations.screen.edit_profile.EditProfileViewModelFactory
import org.example.project.presentations.screen.features.FeaturesScreen
import org.example.project.presentations.screen.features.FeaturesViewModel
import org.example.project.presentations.screen.features.FeaturesViewModelFactory
import org.example.project.presentations.screen.feedback.FeedbackScreen
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
import org.example.project.presentations.screen.transcript.TranscriptViewModel
import org.example.project.presentations.screen.transcript.TranscriptViewModelFactory
import org.example.project.presentations.screen.transcript_term.TranscriptTermScreen
import org.example.project.presentations.screen.transcript_term.TranscriptTermViewModel
import org.example.project.presentations.screen.transcript_term.TranscriptTermViewModelFactory
import org.example.project.presentations.utils.openDialer
import org.example.project.presentations.utils.toRoute

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val sharedViewModel: SharedViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.Splash,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        }
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
                factory = HomeViewModelFactory(
                    container.studentUseCase,
                    container.scheduleUseCase,
                    container.featureRepository
                )
            )
            val scheduleViewModel: ScheduleViewModel = viewModel(
                factory = ScheduleViewModelFactory(container.scheduleUseCase)
            )
            val transcriptViewModel: TranscriptViewModel = viewModel(
                factory = TranscriptViewModelFactory(container.transcriptUseCase)
            )

            MainScreen(
                homeViewModel = homeViewModel,
                scheduleViewModel = scheduleViewModel,
                transcriptViewModel = transcriptViewModel,
                onOpenProfileScreen = {
                    navController.navigate(Routes.Profile)
                },
                onOpenNotificationScreen = {
                    navController.navigate(Routes.Notification)
                },
                onOpenTranscriptTerm = { semesterLabel ->
                    navController.navigate("${Routes.TranscriptTerm}/$semesterLabel")
                },
                onOpenTimetable = {
                    navController.navigate(Routes.TimetableScreen)
                },
                onOpenFeatureScreen = {
                    navController.navigate(Routes.FeaturesScreen)
                }
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

        composable("${Routes.TranscriptTerm}/{semesterLabel}") {
            val container = LocalAppContainer.current
            val transcriptTermViewModel: TranscriptTermViewModel = viewModel(
                factory = TranscriptTermViewModelFactory(container.transcriptUseCase)
            )

            TranscriptTermScreen(
                viewModel = transcriptTermViewModel,
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

        composable(Routes.FeaturesScreen) {
            val container = LocalAppContainer.current
            val context = LocalContext.current
            val featuresViewModel: FeaturesViewModel = viewModel(
                factory = FeaturesViewModelFactory(container.featureRepository)
            )

            FeaturesScreen(
                viewModel = featuresViewModel,
                onBack = { navController.popBackStack() },
                onNavigate = {
                    if (it.type == FeatureType.TRAINING_OFFICE)
                        context.openDialer()
                    else
                        navController.navigate(it.type.toRoute())
                }
            )
        }

        composable(Routes.Feedback) {
            FeedbackScreen(
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