package org.example.project.presentations.navigation

import android.content.Intent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
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
import org.example.project.presentations.components.WebViewScreen
import org.example.project.presentations.screen.alerts_and_actions.AlertsAndActionsScreen
import org.example.project.presentations.screen.alerts_and_actions.AlertsAndActionsViewModel
import org.example.project.presentations.screen.alerts_and_actions.AlertsAndActionsViewModelFactory
import org.example.project.presentations.screen.application.ApplicationScreen
import org.example.project.presentations.screen.application.ApplicationViewModel
import org.example.project.presentations.screen.application.ApplicationViewModelFactory
import org.example.project.presentations.screen.application_detail.ApplicationDetailScreen
import org.example.project.presentations.screen.application_detail.ApplicationDetailViewModel
import org.example.project.presentations.screen.application_detail.ApplicationDetailViewModelFactory
import org.example.project.presentations.screen.attendance_checking.CameraQrScreen
import org.example.project.presentations.screen.attendance_checking.CameraQrViewModel
import org.example.project.presentations.screen.attendance_checking.CameraQrViewModelFactory
import org.example.project.presentations.screen.chat.ChatScreen
import org.example.project.presentations.screen.chat.ChatViewModel
import org.example.project.presentations.screen.chat.ChatViewModelFactory
import org.example.project.presentations.screen.class_sign_up.ClassSignUpScreen
import org.example.project.presentations.screen.class_sign_up.ClassSignUpViewModel
import org.example.project.presentations.screen.class_sign_up.ClassSignUpViewModelFactory
import org.example.project.presentations.screen.class_signed_up.SignedUpClassViewModelFactory
import org.example.project.presentations.screen.class_signed_up.SignedUpClassesScreen
import org.example.project.presentations.screen.class_signed_up.SignedUpClassesViewModel
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
import org.example.project.presentations.screen.feedback.FeedbackViewModel
import org.example.project.presentations.screen.feedback.FeedbackViewModelFactory
import org.example.project.presentations.screen.feedback_detail.FeedbackDetailScreen
import org.example.project.presentations.screen.gpa_predict.GpaPredictScreen
import org.example.project.presentations.screen.gpa_predict.GpaPredictViewModel
import org.example.project.presentations.screen.gpa_predict.GpaPredictViewModelFactory
import org.example.project.presentations.screen.gpa_tracker.GpaTrackerScreen
import org.example.project.presentations.screen.gpa_tracker.GpaTrackerViewModel
import org.example.project.presentations.screen.gpa_tracker.GpaTrackerViewModelFactory
import org.example.project.presentations.screen.home.HomeViewModel
import org.example.project.presentations.screen.home.HomeViewModelFactory
import org.example.project.presentations.screen.login.LoginScreen
import org.example.project.presentations.screen.login.LoginViewModel
import org.example.project.presentations.screen.login.LoginViewModelFactory
import org.example.project.presentations.screen.main.LocalAppContainer
import org.example.project.presentations.screen.main.MainScreen
import org.example.project.presentations.screen.message.MessageScreen
import org.example.project.presentations.screen.message.MessageViewModel
import org.example.project.presentations.screen.message.MessageViewModelFactory
import org.example.project.presentations.screen.messages.MessagesViewModel
import org.example.project.presentations.screen.messages.MessagesViewModelFactory
import org.example.project.presentations.screen.news.NewsScreen
import org.example.project.presentations.screen.news.NewsViewModel
import org.example.project.presentations.screen.news.NewsViewModelFactory
import org.example.project.presentations.screen.notification.NotificationScreen
import org.example.project.presentations.screen.notification.NotificationViewModel
import org.example.project.presentations.screen.notification.NotificationViewModelFactory
import org.example.project.presentations.screen.notification_detail.NotificationDetailScreen
import org.example.project.presentations.screen.notification_detail.NotificationDetailViewModel
import org.example.project.presentations.screen.notification_detail.NotificationDetailViewModelFactory
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
import org.example.project.presentations.screen.temp_timetable.TempTimetableScreen
import org.example.project.presentations.screen.temp_timetable.TempTimetableViewModel
import org.example.project.presentations.screen.temp_timetable.TempTimetableViewModelFactory
import org.example.project.presentations.screen.timetable.TimetableScreen
import org.example.project.presentations.screen.timetable.TimetableViewModel
import org.example.project.presentations.screen.timetable.TimetableViewModelFactory
import org.example.project.presentations.screen.timetable_offline.OfflineTimetableScreen
import org.example.project.presentations.screen.timetable_offline.OfflineTimetableViewModel
import org.example.project.presentations.screen.timetable_offline.OfflineTimetableViewModelFactory
import org.example.project.presentations.screen.transcript.TranscriptViewModel
import org.example.project.presentations.screen.transcript.TranscriptViewModelFactory
import org.example.project.presentations.screen.transcript_term.TranscriptTermScreen
import org.example.project.presentations.screen.transcript_term.TranscriptTermViewModel
import org.example.project.presentations.screen.transcript_term.TranscriptTermViewModelFactory
import org.example.project.presentations.screen.tuition_payment.TuitionPaymentScreen
import org.example.project.presentations.screen.tuition_payment.TuitionPaymentViewModel
import org.example.project.presentations.screen.tuition_payment.TuitionPaymentViewModelFactory
import org.example.project.presentations.utils.NotificationPermissionManager
import org.example.project.presentations.utils.openDialer
import org.example.project.presentations.utils.openEmail
import org.example.project.presentations.utils.toRoute
import java.net.URLEncoder

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
            val factory = remember(container) {
                LoginViewModelFactory(
                    container.deviceProvider,
                    container.handleLoginSuccessUseCase
                )
            }
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
                    container.featureRepository,
                    container.newsRepository,
                    container.quoteRepository,
                    container.authPluginConfig,
                    container.notificationRepository,
                    container.examScheduleRepository,
                    container.semesterUseCase
                )
            }
            val scheduleFactory = remember(container) {
                ScheduleViewModelFactory(
                    container.scheduleUseCase,
                    container.notificationRepository
                )
            }
            val transcriptFactory = remember(container) {
                TranscriptViewModelFactory(
                    container.transcriptUseCase,
                    container.notificationRepository
                )
            }

            val messagesViewModelFactory = remember(container) {
                MessagesViewModelFactory(
                    container.messageRepository,
                    container.studentUseCase,
                    container.userRepository
                )
            }

            val homeViewModel: HomeViewModel = viewModel(factory = homeFactory)
            val scheduleViewModel: ScheduleViewModel = viewModel(factory = scheduleFactory)
            val transcriptViewModel: TranscriptViewModel = viewModel(factory = transcriptFactory)
            val messagesViewModel: MessagesViewModel = viewModel(factory = messagesViewModelFactory)

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
                onOpenFeature = { navController.navigate(it.toRoute()) },
                onOpenNewsScreen = { navController.navigate(AppRoute.NewsScreen) },
//                onOpenNews = { url ->
//                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
//                    context.startActivity(intent)
//                },
                onOpenNews = { url ->
                    navController.navigate("webview?url=${URLEncoder.encode(url, "UTF-8")}")
                },
                onOpenChat = { navController.navigate(AppRoute.Chat) },
                onOpenGpaTracker = { navController.navigate(AppRoute.GpaTracker) },
                openAlertsAndActionsScreen = { navController.navigate(AppRoute.AlertsAndActions) },
                onViewMaterials = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        "https://elearning.thanglong.edu.vn/login/index.php".toUri()
                    )
                    context.startActivity(intent)
                },
                messagesViewModel = messagesViewModel,
                onOpenMessage = { roomId, studentId, chatName -> 
                    navController.navigate(AppRoute.messageDetail(roomId, studentId, chatName)) 
                }
            )
        }

        composable(
            route = "webview?url={url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            val url = backStackEntry.arguments?.getString("url") ?: ""

            WebViewScreen(url = url, onClose = {
                navController.popBackStack()
            })
        }

        composable(AppRoute.AlertsAndActions) {
            val container = LocalAppContainer.current
            val factory = remember(container) {
                AlertsAndActionsViewModelFactory(
                    container.notificationRepository
                )
            }
            val viewModel: AlertsAndActionsViewModel = viewModel(factory = factory)
            AlertsAndActionsScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onNavigateToExamSchedule = { navController.navigate(AppRoute.ExamSchedule) },
                onNavigateToTuition = { navController.navigate(AppRoute.TuitionPayment) }
            )
        }

        composable(AppRoute.Profile) {
            val container = LocalAppContainer.current
            val factory = remember(container) {
                ProfileViewModelFactory(
                    container.studentUseCase,
                    container.authPluginConfig
                )
            }
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
            val context = LocalContext.current
            val factory = remember(container) {
                SettingViewModelFactory(
                    container.logoutUseCase,
                    permissionManager = NotificationPermissionManager(context),
                    prefs = container.appPreferences
                )
            }
            val settingViewModel: SettingViewModel = viewModel(factory = factory)
            SettingScreen(
                viewModel = settingViewModel,
                resetAppData = resetAppData,
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppRoute.EditProfile) {
            val container = LocalAppContainer.current
            val factory =
                remember(container) {
                    EditProfileViewModelFactory(
                        container.studentUseCase,
                        container.authPluginConfig
                    )
                }
            val editProfileViewModel: EditProfileViewModel = viewModel(factory = factory)

            EditProfileScreen(
                viewModel = editProfileViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppRoute.Notification) {
            val container = LocalAppContainer.current
            val factory =
                remember(container) { NotificationViewModelFactory(container.notificationRepository) }
            val notificationViewModel: NotificationViewModel = viewModel(factory = factory)
            NotificationScreen(
                viewModel = notificationViewModel,
                onBack = { navController.popBackStack() },
                onOpenNotificationDetail = { id ->
                    navController.navigate(AppRoute.notificationDetail(id))
                }
            )
        }

        composable(
            route = AppRoute.NotificationDetail,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) {
            val container = LocalAppContainer.current
            val factory = remember(container) {
                NotificationDetailViewModelFactory(container.notificationRepository)
            }
            val viewModel: NotificationDetailViewModel = viewModel(factory = factory)
            NotificationDetailScreen(
                onBack = { navController.popBackStack() },
                viewModel = viewModel
            )
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
                onOpenEmail = { email -> context.openEmail(email) },
                onViewMaterials = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        "https://elearning.thanglong.edu.vn/login/index.php".toUri()
                    )
                    context.startActivity(intent)
                }
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
                onOpenEmail = { email -> context.openEmail(email) },
                onViewMaterials = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        "https://elearning.thanglong.edu.vn/login/index.php".toUri()
                    )
                    context.startActivity(intent)
                }
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
            val container = LocalAppContainer.current
            val factory = FeedbackViewModelFactory(
                container.feedbackRepository
            )
            val feedbackViewModel: FeedbackViewModel = viewModel(factory = factory)

            FeedbackScreen(
                viewModel = feedbackViewModel,
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
                    GenerateQrUseCase(),
                    container.authPluginConfig
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

        composable(AppRoute.ClassSignUp) {
            val container = LocalAppContainer.current
            val factory = remember(container) {
                ClassSignUpViewModelFactory(
                    container.enrollmentRepository,
                    container.semesterUseCase
                )
            }
            val classSignUpViewModel: ClassSignUpViewModel = viewModel(factory = factory)

            ClassSignUpScreen(
                viewModel = classSignUpViewModel,
                onBack = { navController.popBackStack() },
                onOpenSignedUpClass = {
                    navController.navigate(AppRoute.ClassSignUpDetail)
                }
            )
        }

        composable(AppRoute.TuitionPayment) {
            val container = LocalAppContainer.current
            val factory = remember(container) {
                TuitionPaymentViewModelFactory(
                    container.tuitionRepository,
                    container.paymentRepository
                )
            }
            val tuitionPaymentViewModel: TuitionPaymentViewModel = viewModel(factory = factory)
            TuitionPaymentScreen(
                viewModel = tuitionPaymentViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppRoute.NewsScreen) {
            val container = LocalAppContainer.current
            val factory = remember(container) {
                NewsViewModelFactory(
                    container.newsRepository
                )
            }
            val newsViewModel: NewsViewModel = viewModel(factory = factory)
            NewsScreen(
                viewModel = newsViewModel,
                onBack = { navController.popBackStack() },
                onOpenNews = { url ->
                    navController.navigate("webview?url=${URLEncoder.encode(url, "UTF-8")}")
                }
            )
        }

        composable(AppRoute.Chat) {
            val container = LocalAppContainer.current
            val factory = remember(container) {
                ChatViewModelFactory(
                    container.chatRepository
                )
            }
            val chatViewModel: ChatViewModel = viewModel(factory = factory)
            ChatScreen(
                viewModel = chatViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppRoute.Application) {
            val container = LocalAppContainer.current
            val factory = remember(container) {
                ApplicationViewModelFactory(
                    container.applicationRepository
                )
            }
            val newsViewModel: ApplicationViewModel = viewModel(factory = factory)
            ApplicationScreen(
                viewModel = newsViewModel,
                onBack = { navController.popBackStack() },
                onNavigateToDetail = { id ->
                    navController.navigate(AppRoute.applicationDetail(id))
                }
            )
        }

        composable(
            route = AppRoute.ApplicationDetail,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: -1
            val container = LocalAppContainer.current
            val context = LocalContext.current
            val factory = remember(container, id) {
                ApplicationDetailViewModelFactory(
                    container.applicationRepository,
                    id
                )
            }
            val viewModel: ApplicationDetailViewModel = viewModel(factory = factory)
            ApplicationDetailScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() },
                onOpenUrl = { url ->
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    context.startActivity(intent)
                }
            )
        }

        composable(AppRoute.GpaTracker) {
            val container = LocalAppContainer.current
            val factory = remember(container) {
                GpaTrackerViewModelFactory(
                    container.transcriptUseCase
                )
            }
            val gpaTrackerViewModel: GpaTrackerViewModel = viewModel(factory = factory)
            GpaTrackerScreen(
                viewModel = gpaTrackerViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(AppRoute.ClassSignUpDetail) {
            val container = LocalAppContainer.current
            val factory = remember(container) {
                SignedUpClassViewModelFactory(
                    container.enrollmentRepository,
                    container.semesterUseCase
                )
            }
            val signedUpClassesViewModel: SignedUpClassesViewModel = viewModel(factory = factory)
            SignedUpClassesScreen(
                viewModel = signedUpClassesViewModel,
                onBack = { navController.popBackStack() },
                onOpenTempSchedule = { navController.navigate(AppRoute.TempSchedule) },
                onBackToHome = {
                    navController.navigate(AppRoute.Main) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(AppRoute.TempSchedule) {
            val container = LocalAppContainer.current
            val context = LocalContext.current
            val factory =
                remember(container) {
                    TempTimetableViewModelFactory(
                        container.enrollmentRepository,
                        container.semesterUseCase
                    )
                }
            val tempTimetableViewModel: TempTimetableViewModel = viewModel(factory = factory)

            TempTimetableScreen(
                viewModel = tempTimetableViewModel,
                onBack = { navController.popBackStack() },
                onOpenEmail = { email -> context.openEmail(email) },
                onViewMaterials = {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        "https://elearning.thanglong.edu.vn/login/index.php".toUri()
                    )
                    context.startActivity(intent)
                }
            )
        }

        composable(AppRoute.AttendanceChecking) {
            val container = LocalAppContainer.current
            val factory = remember(container) {
                CameraQrViewModelFactory(
                    container.attendanceRepository,
                    container.getLocationUseCase,
                    container.locationRepository
                )
            }
            val cameraQrViewModel: CameraQrViewModel = viewModel(factory = factory)

            CameraQrScreen(
                viewModel = cameraQrViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = AppRoute.MessageRoute,
            arguments = listOf(
                navArgument("roomId") {
                    type = NavType.StringType
                },
                navArgument("studentId") {
                    type = NavType.StringType
                },
                navArgument("chatName") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val container = LocalAppContainer.current
            val factory = remember(container) {
                MessageViewModelFactory(
                    container.messageRepository,
                    container.studentUseCase
                )
            }

            val viewModel: MessageViewModel = viewModel(
                viewModelStoreOwner = backStackEntry,
                factory = factory
            )

            MessageScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}