package org.example.project.di

import org.example.project.DeviceProvider
import org.example.project.data.local.AppPreferences
import org.example.project.data.local.FirebaseStorage
import org.example.project.data.local.ImageBase64Storage
import org.example.project.data.local.TokenStorage
import org.example.project.data.local.createDatabase
import org.example.project.data.local.getDatabaseBuilder
import org.example.project.data.remote.api.ApplicationApi
import org.example.project.data.remote.api.AuthApi
import org.example.project.data.remote.api.AttendanceApi
import org.example.project.data.remote.api.ChatApi
import org.example.project.data.remote.api.EnrollmentApi
import org.example.project.data.remote.api.ExamScheduleApi
import org.example.project.data.remote.api.NewsApi
import org.example.project.data.remote.api.NotificationApi
import org.example.project.data.remote.api.PaymentApi
import org.example.project.data.remote.api.QuoteApi
import org.example.project.data.remote.api.ScheduleApi
import org.example.project.data.remote.api.SemesterApi
import org.example.project.data.remote.api.StudentApi
import org.example.project.data.remote.api.StudentClassApi
import org.example.project.data.remote.api.StudyProgramApi
import org.example.project.data.remote.api.TranscriptApi
import org.example.project.data.remote.api.TuitionApi
import org.example.project.data.remote.createExternalHttpClient
import org.example.project.data.remote.createExternalHttpClientWithAuthPlugin
import org.example.project.data.remote.createHttpClient
import org.example.project.data.remote.interceptor.AuthPluginConfig
import org.example.project.domain.TopicSubscriber
import org.example.project.domain.repository.ApplicationRepository
import org.example.project.domain.repository.AuthRepository
import org.example.project.domain.repository.ChatRepository
import org.example.project.domain.repository.AttendanceRepository
import org.example.project.domain.repository.EnrollmentRepository
import org.example.project.domain.repository.ExamScheduleRepository
import org.example.project.domain.repository.FeatureRepository
import org.example.project.domain.repository.LocationRepository
import org.example.project.domain.repository.MessageRepository
import org.example.project.domain.repository.NewsRepository
import org.example.project.domain.repository.NotificationRepository
import org.example.project.domain.repository.PaymentRepository
import org.example.project.domain.repository.QuoteRepository
import org.example.project.domain.repository.ScheduleRepository
import org.example.project.domain.repository.SemesterRepository
import org.example.project.domain.repository.StudentClassRepository
import org.example.project.domain.repository.StudentRepository
import org.example.project.domain.repository.TranscriptRepository
import org.example.project.domain.repository.TuitionRepository
import org.example.project.domain.usecase.GetLocationUseCase
import org.example.project.domain.usecase.HandleLoginSuccessUseCase
import org.example.project.domain.usecase.LogoutUseCase
import org.example.project.domain.usecase.ScheduleUseCase
import org.example.project.domain.usecase.SemesterUseCase
import org.example.project.domain.usecase.StudentUseCase
import org.example.project.domain.usecase.TranscriptUseCase

class AppContainer(
    tokenStorage: TokenStorage,
    imageStorage: ImageBase64Storage,
    firebaseStorage: FirebaseStorage,
    //for deviceId
    val deviceProvider: DeviceProvider,
    //for notification
    val appPreferences: AppPreferences,
    topicSubscriber: TopicSubscriber,
    val locationRepository: LocationRepository,
    context: Any? = null,
    val messageRepository: MessageRepository
) {
    private val httpClient = createHttpClient(tokenStorage)
    private val externalHttpClient = createExternalHttpClient()
    private val chatHttpClient = createExternalHttpClientWithAuthPlugin(tokenStorage)

    // for auth
    private val authApi = AuthApi(httpClient)
    private val authRepository = AuthRepository(
        authApi = authApi,
        tokenStorage = tokenStorage,
        imageStorage = imageStorage,
        firebaseStorage = firebaseStorage
    )

    val authPluginConfig = AuthPluginConfig().apply {
        this.imageStorage = imageStorage
    }

    //for database
    private val database = createDatabase(getDatabaseBuilder(context))

    //for student
    private val studentApi = StudentApi(httpClient)
    private val studentRepository = StudentRepository(studentApi)
    val studentUseCase = StudentUseCase(studentRepository)

    //for schedule
    private val scheduleDao = database.scheduleDao()
    private val scheduleApi = ScheduleApi(httpClient)
    private val scheduleRepository = ScheduleRepository(scheduleApi, scheduleDao)
    val scheduleUseCase = ScheduleUseCase(scheduleRepository)

    //for features
    private val featureDao = database.featureDao()
    val featureRepository = FeatureRepository(featureDao)
    private val newsApi = NewsApi(httpClient)
    val newsRepository = NewsRepository(newsApi)
    private val quoteDao = database.quoteDao()
    private val quoteApi = QuoteApi(externalHttpClient)
    val quoteRepository = QuoteRepository(quoteApi, quoteDao)

    //for transcript
    private val transcriptApi = TranscriptApi(httpClient)
    private val studyProgramApi = StudyProgramApi(httpClient)
    private val transcriptRepository = TranscriptRepository(transcriptApi, studyProgramApi)
    val transcriptUseCase = TranscriptUseCase(transcriptRepository)

    //for exam schedule
    private val semesterDao = database.semesterDao()
    private val semesterApi = SemesterApi(httpClient)
    private val semesterRepository = SemesterRepository(semesterApi, semesterDao)
    private val examScheduleApi = ExamScheduleApi(httpClient)
    val semesterUseCase = SemesterUseCase(semesterRepository)
    val examScheduleRepository = ExamScheduleRepository(examScheduleApi)

    //for setting
    val logoutUseCase = LogoutUseCase(authRepository)

    //for studentClass
    private val studentClassApi = StudentClassApi(httpClient)
    val studentClassRepository = StudentClassRepository(studentClassApi)

    //for tuition
    private val tuitionApi = TuitionApi(httpClient)
    val tuitionRepository = TuitionRepository(tuitionApi)
    private val paymentApi = PaymentApi(httpClient)
    val paymentRepository = PaymentRepository(paymentApi)

    //for notification
    private val notificationDao = database.notificationDao()
    private val notificationApi = NotificationApi(httpClient)
    private val alertDao = database.alertDao()
    val notificationRepository = NotificationRepository(
        notificationApi,
        firebaseStorage,
        topicSubscriber,
        notificationDao,
        alertDao
    )

    val handleLoginSuccessUseCase =
        HandleLoginSuccessUseCase(authRepository, notificationRepository)

    //for application
    private val applicationApi = ApplicationApi(httpClient)
    val applicationRepository = ApplicationRepository(applicationApi)

    //for feedback
    private val feedbackApi = org.example.project.data.remote.api.FeedbackApi(httpClient)
    val feedbackRepository = org.example.project.domain.repository.FeedbackRepository(feedbackApi)

    //for chat
    private val chatApi = ChatApi(httpClient)
    val chatRepository = ChatRepository(chatHttpClient, chatApi)

    //for enrollment
    private val enrollmentApi = EnrollmentApi(httpClient)
    val enrollmentRepository = EnrollmentRepository(enrollmentApi, studyProgramApi)

    //for attendance
    private val attendanceApi = AttendanceApi(httpClient)
    val attendanceRepository = AttendanceRepository(attendanceApi)
    val getLocationUseCase = GetLocationUseCase(locationRepository)
}