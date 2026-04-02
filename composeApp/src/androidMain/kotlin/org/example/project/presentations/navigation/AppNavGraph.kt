package org.example.project.presentations.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import org.example.project.domain.usecase.CountdownTimerUseCase
import org.example.project.domain.usecase.GenerateQrUseCase
import org.example.project.domain.usecase.GpaPredictUseCase
import org.example.project.presentations.screen.class_sign_up.ClassSignUpScreen
import org.example.project.presentations.screen.digital_student_card.DigitalStudentCardScreen
import org.example.project.presentations.screen.digital_student_card.DigitalStudentCardViewModel
import org.example.project.presentations.screen.digital_student_card.DigitalStudentCardViewModelFactory
import org.example.project.presentations.screen.edit_profile.EditProfileScreen
import org.example.project.presentations.screen.edit_profile.EditProfileViewModel
import org.example.project.presentations.screen.edit_profile.EditProfileViewModelFactory
import org.example.project.presentations.screen.exam_schedule.ExamScheduleScreen
import org.example.project.presentations.screen.exam_schedule.ExamScheduleViewModel
import org.example.project.presentations.screen.exam_schedule.ExamScheduleViewModelFactory
import org.example.project.presentations.screen.features.FeaturesScreen
import org.example.project.presentations.screen.features.FeaturesViewModel
import org.example.project.presentations.screen.features.FeaturesViewModelFactory
import org.example.project.presentations.screen.feedback.FeedbackScreen
import org.example.project.presentations.screen.feedback_detail.FeedbackDetailScreen
import org.example.project.presentations.screen.gpa_predict.GpaPredictScreen
import org.example.project.presentations.screen.gpa_predict.GpaPredictViewModel
import org.example.project.presentations.screen.gpa_predict.GpaPredictViewModelFactory
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
import org.example.project.presentations.screen.setting.SettingViewModel
import org.example.project.presentations.screen.setting.SettingViewModelFactory
import org.example.project.presentations.screen.splash.SplashScreen
import org.example.project.presentations.screen.student_class.StudentClassScreen
import org.example.project.presentations.screen.student_class.StudentClassViewModel
import org.example.project.presentations.screen.student_class.StudentClassViewModelFactory
import org.example.project.presentations.screen.timetable_offline.OfflineTimetableScreen
import org.example.project.presentations.screen.timetable_offline.OfflineTimetableViewModel
import org.example.project.presentations.screen.timetable_offline.OfflineTimetableViewModelFactory
import org.example.project.presentations.screen.timetable.TimetableScreen
import org.example.project.presentations.screen.timetable.TimetableViewModel
import org.example.project.presentations.screen.timetable.TimetableViewModelFactory
import org.example.project.presentations.screen.transcript.TranscriptViewModel
import org.example.project.presentations.screen.transcript.TranscriptViewModelFactory
import org.example.project.presentations.screen.transcript_term.TranscriptTermScreen
import org.example.project.presentations.screen.transcript_term.TranscriptTermViewModel
import org.example.project.presentations.screen.transcript_term.TranscriptTermViewModelFactory
import org.example.project.presentations.utils.openDialer
import org.example.project.presentations.utils.openEmail
import org.example.project.presentations.utils.toRoute

@Composable
fun AppNavGraph(
    resetAppData: () -> Unit
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppRoute.Splash,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(400))
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(400, easing = FastOutSlowInEasing)
            ) + fadeOut(animationSpec = tween(400))
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400, easing = FastOutSlowInEasing)
            ) + fadeIn(animationSpec = tween(400))
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(400, easing = FastOutSlowInEasing)
            ) + fadeOut(animationSpec = tween(400))
        }
    ) {
        composable(AppRoute.Splash) {
            SplashScreen(navController)
        }

        composable(AppRoute.Login) {
            val container = LocalAppContainer.current
            val factory = remember(container) { LoginViewModelFactory(container.loginUseCase) }
            val loginViewModel: LoginViewModel = viewModel(factory = factory)

            LoginScreen(
                onNavigateToHome = {
                    navController.navigate(AppRoute.Main) {
                        popUpTo(AppRoute.Login) { inclusive = true }
                    }
                },
                onNavigateToOfflineTimetable = {
                    navController.navigate(AppRoute.OfflineTimetableScreen)
                },
                loginViewModel = loginViewModel
            )
        }

        composable(AppRoute.Main) {
            val container = LocalAppContainer.current
            val context = LocalContext.current

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
                onOpenProfileScreen = { navController.navigate(AppRoute.Profile) },
                onOpenNotificationScreen = { navController.navigate(AppRoute.Notification) },
                onOpenTranscriptTerm = { semesterLabel, academicYear ->
                    navController.navigate("${AppRoute.TranscriptTerm}/$academicYear/$semesterLabel")
                },
                onOpenTimetable = { navController.navigate(AppRoute.TimetableScreen) },
                onOpenFeatureScreen = { navController.navigate(AppRoute.FeaturesScreen) },
                onSendEmail = { email -> context.openEmail(email) },
                onOpenFeature = { navController.navigate(it.toRoute()) }
            )
        }

        composable(AppRoute.Profile) {
            val container = LocalAppContainer.current
            val factory = remember(container) { ProfileViewModelFactory(container.studentUseCase) }
            val profileViewModel: ProfileViewModel = viewModel(factory = factory)

            ProfileScreen(
                onBack = { navController.popBackStack() },
                onOpenSetting = { navController.navigate(AppRoute.Setting) },
                onOpenEditProfile = { navController.navigate(AppRoute.EditProfile) },
                viewModel = profileViewModel,
            )
        }

        composable(AppRoute.Setting) {
            val container = LocalAppContainer.current
            val factory = remember(container) { SettingViewModelFactory(container.logoutUseCase) }
            val settingViewModel: SettingViewModel = viewModel(factory = factory)
            SettingScreen(
                viewModel = settingViewModel,
                resetAppData = resetAppData,
                onBack = { navController.popBackStack() })
        }

        composable(AppRoute.EditProfile) {
            val container = LocalAppContainer.current
            val factory =
                remember(container) { EditProfileViewModelFactory(container.studentUseCase) }
            val editProfileViewModel: EditProfileViewModel = viewModel(factory = factory)

            EditProfileScreen(
                viewModel = editProfileViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppRoute.Notification) {
            NotificationScreen(onBack = { navController.popBackStack() })
        }

        composable(
            route = "${AppRoute.TranscriptTerm}/{academicYear}/{semesterLabel}",
            arguments = listOf(
                navArgument("academicYear") { type = NavType.StringType },
                navArgument("semesterLabel") { type = NavType.StringType }
            )
        ) {
            val container = LocalAppContainer.current
            val factory =
                remember(container) { TranscriptTermViewModelFactory(container.transcriptUseCase) }
            val transcriptTermViewModel: TranscriptTermViewModel = viewModel(factory = factory)

            TranscriptTermScreen(
                viewModel = transcriptTermViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppRoute.TimetableScreen) {
            val container = LocalAppContainer.current
            val context = LocalContext.current
            val factory =
                remember(container) {
                    TimetableViewModelFactory(
                        container.scheduleUseCase,
                        container.semesterUseCase
                    )
                }
            val timetableViewModel: TimetableViewModel = viewModel(factory = factory)

            TimetableScreen(
                viewModel = timetableViewModel,
                onBack = { navController.popBackStack() },
                onOpenEmail = { email -> context.openEmail(email) }
            )
        }

        composable(AppRoute.OfflineTimetableScreen) {
            val container = LocalAppContainer.current
            val context = LocalContext.current
            val factory =
                remember(container) {
                    OfflineTimetableViewModelFactory(
                        container.scheduleUseCase,
                        container.semesterUseCase
                    )
                }
            val offlineTimetableViewModel: OfflineTimetableViewModel = viewModel(factory = factory)

            OfflineTimetableScreen(
                viewModel = offlineTimetableViewModel,
                onBack = { navController.popBackStack() },
                onOpenEmail = { email -> context.openEmail(email) }
            )
        }

        composable(AppRoute.ExamSchedule) {
            val container = LocalAppContainer.current
            val factory = remember(container) {
                ExamScheduleViewModelFactory(
                    container.examScheduleRepository,
                    container.semesterUseCase
                )
            }
            val examScheduleViewModel: ExamScheduleViewModel = viewModel(factory = factory)

            ExamScheduleScreen(
                viewModel = examScheduleViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppRoute.FeaturesScreen) {
            val container = LocalAppContainer.current
            val context = LocalContext.current
            val factory =
                remember(container) { FeaturesViewModelFactory(container.featureRepository) }
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

        composable(AppRoute.GpaPredict) {
            val container = LocalAppContainer.current
            val gpaPredictUseCase = GpaPredictUseCase()
            val factory = remember(container) {
                GpaPredictViewModelFactory(
                    container.semesterUseCase,
                    container.transcriptUseCase,
                    container.scheduleUseCase,
                    gpaPredictUseCase
                )
            }
            val gpaPredictViewModel: GpaPredictViewModel = viewModel(factory = factory)
            GpaPredictScreen(
                viewModel = gpaPredictViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppRoute.Feedback) {
            FeedbackScreen(
                viewModel = viewModel(), // TODO:
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppRoute.FeedbackDetail) {
            FeedbackDetailScreen(onBack = { navController.popBackStack() })
        }

        composable(AppRoute.DigitalStudentCard) {
            val container = LocalAppContainer.current
            val factory = remember(container) {
                DigitalStudentCardViewModelFactory(
                    container.studentUseCase,
                    CountdownTimerUseCase(),
                    GenerateQrUseCase()
                )
            }
            val digitalStudentCardViewModel: DigitalStudentCardViewModel =
                viewModel(factory = factory)
            DigitalStudentCardScreen(
                digitalStudentCardViewModel,
                onBack = { navController.popBackStack() })
        }

        composable(AppRoute.StudentClass) {
            val container = LocalAppContainer.current
            val factory = remember(container) {
                StudentClassViewModelFactory(
                    container.studentClassRepository
                )
            }
            val studentClassViewModel: StudentClassViewModel = viewModel(factory = factory)

            StudentClassScreen(
                viewModel = studentClassViewModel,
                onBack = { navController.popBackStack() })
        }

        composable(AppRoute.ClassSignUp){
            val container = LocalAppContainer.current
            ClassSignUpScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}