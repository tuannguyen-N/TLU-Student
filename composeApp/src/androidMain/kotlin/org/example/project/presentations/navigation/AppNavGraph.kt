package org.example.project.presentations.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.example.project.domain.model.FeatureType
import org.example.project.domain.model.FeatureUiModel
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
import org.example.project.presentations.screen.timetable.TimetableViewModel
import org.example.project.presentations.screen.timetable.TimetableViewModelFactory
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

    NavHost(
        navController = navController,
        startDestination = Routes.Splash,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
        }
    ) {
        composable(Routes.Splash) {
            SplashScreen(navController)
        }

        composable(Routes.Login) {
            val container = LocalAppContainer.current
            val factory = remember(container) { LoginViewModelFactory(container.loginUseCase) }
            val loginViewModel: LoginViewModel = viewModel(factory = factory)

            LoginScreen(
                onNavigateToHome = { navController.navigate(Routes.Main) },
                loginViewModel = loginViewModel
            )
        }

        composable(Routes.Main) {
            val container = LocalAppContainer.current

            val homeFactory = remember(container) {
                HomeViewModelFactory(
                    container.studentUseCase,
                    container.scheduleUseCase,
                    container.featureRepository
                )
            }
            val scheduleFactory = remember(container) {
                ScheduleViewModelFactory(container.scheduleUseCase)
            }
            val transcriptFactory = remember(container) {
                TranscriptViewModelFactory(container.transcriptUseCase)
            }

            val homeViewModel: HomeViewModel = viewModel(factory = homeFactory)
            val scheduleViewModel: ScheduleViewModel = viewModel(factory = scheduleFactory)
            val transcriptViewModel: TranscriptViewModel = viewModel(factory = transcriptFactory)

            MainScreen(
                homeViewModel = homeViewModel,
                scheduleViewModel = scheduleViewModel,
                transcriptViewModel = transcriptViewModel,
                onOpenProfileScreen = { navController.navigate(Routes.Profile) },
                onOpenNotificationScreen = { navController.navigate(Routes.Notification) },
                onOpenTranscriptTerm = { semesterLabel, academicYear ->
                    navController.navigate("${Routes.TranscriptTerm}/$academicYear/$semesterLabel")
                },
                onOpenTimetable = { navController.navigate(Routes.TimetableScreen) },
                onOpenFeatureScreen = { navController.navigate(Routes.FeaturesScreen) }
            )
        }

        composable(Routes.Profile) {
            val container = LocalAppContainer.current
            val factory = remember(container) { ProfileViewModelFactory(container.studentUseCase) }
            val profileViewModel: ProfileViewModel = viewModel(factory = factory)

            ProfileScreen(
                onBack = { navController.popBackStack() },
                onOpenSetting = { navController.navigate(Routes.Setting) },
                onOpenEditProfile = { navController.navigate(Routes.EditProfile) },
                viewModel = profileViewModel,
            )
        }

        composable(Routes.Setting) {
            SettingScreen(onBack = { navController.popBackStack() })
        }

        composable(Routes.EditProfile) {
            val container = LocalAppContainer.current
            val factory = remember(container) { EditProfileViewModelFactory(container.studentUseCase) }
            val editProfileViewModel: EditProfileViewModel = viewModel(factory = factory)

            EditProfileScreen(
                viewModel = editProfileViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.Notification) {
            NotificationScreen(onBack = { navController.popBackStack() })
        }

        composable(
            route = "${Routes.TranscriptTerm}/{academicYear}/{semesterLabel}",
            arguments = listOf(
                navArgument("academicYear") { type = NavType.StringType },
                navArgument("semesterLabel") { type = NavType.StringType }
            )
        ) {
            val container = LocalAppContainer.current
            val factory = remember(container) { TranscriptTermViewModelFactory(container.transcriptUseCase) }
            val transcriptTermViewModel: TranscriptTermViewModel = viewModel(factory = factory)

            TranscriptTermScreen(
                viewModel = transcriptTermViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.TimetableScreen) {
            val container = LocalAppContainer.current
            val factory = remember(container) { TimetableViewModelFactory(container.scheduleUseCase) }
            val timetableViewModel: TimetableViewModel = viewModel(factory = factory)

            TimetableScreen(
                viewModel = timetableViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.FeaturesScreen) {
            val container = LocalAppContainer.current
            val context = LocalContext.current
            val factory = remember(container) { FeaturesViewModelFactory(container.featureRepository) }
            val featuresViewModel: FeaturesViewModel = viewModel(factory = factory)

            val onNavigate: (FeatureUiModel) -> Unit = remember {
                { feature ->
                    if (feature.type == FeatureType.TRAINING_OFFICE)
                        context.openDialer()
                    else
                        navController.navigate(feature.type.toRoute())
                }
            }

            FeaturesScreen(
                viewModel = featuresViewModel,
                onBack = { navController.popBackStack() },
                onNavigate = onNavigate
            )
        }

        composable(Routes.Feedback) {
            FeedbackScreen(
                viewModel = viewModel(), // TODO:
                onBack = { navController.popBackStack() }
            )
        }

        composable(Routes.FeedbackDetail) {
            FeedbackDetailScreen(onBack = { navController.popBackStack() })
        }
    }
}