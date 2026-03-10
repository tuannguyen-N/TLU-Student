package org.example.project.di

import org.example.project.data.local.TokenStorage
import org.example.project.data.local.createDatabase
import org.example.project.data.local.getDatabaseBuilder
import org.example.project.data.remote.api.AuthApi
import org.example.project.data.remote.api.ScheduleApi
import org.example.project.data.remote.api.StudentApi
import org.example.project.data.remote.api.StudyProgramApi
import org.example.project.data.remote.api.TranscriptApi
import org.example.project.data.remote.createHttpClient
import org.example.project.domain.repository.AuthRepository
import org.example.project.domain.repository.FeatureRepository
import org.example.project.domain.repository.ScheduleRepository
import org.example.project.domain.repository.StudentRepository
import org.example.project.domain.repository.TranscriptRepository
import org.example.project.domain.usecase.LoginUseCase
import org.example.project.domain.usecase.ScheduleUseCase
import org.example.project.domain.usecase.StudentUseCase
import org.example.project.domain.usecase.TranscriptUseCase

class AppContainer(
    tokenStorage: TokenStorage,
    context: Any? = null
) {
    private val httpClient = createHttpClient(tokenStorage)

    // for auth
    private val authApi = AuthApi(httpClient)
    private val authRepository = AuthRepository(
        authApi = authApi,
        tokenStorage = tokenStorage
    )
    val loginUseCase = LoginUseCase(authRepository)

    //for database
    private val database = createDatabase(getDatabaseBuilder(context))

    //for student
    private val studentApi = StudentApi(httpClient)
    private val studentRepository = StudentRepository(studentApi)
    val studentUseCase = StudentUseCase(studentRepository)

    //for schedule
    private val scheduleApi = ScheduleApi(httpClient)
    private val scheduleRepository = ScheduleRepository(scheduleApi)
    val scheduleUseCase = ScheduleUseCase(scheduleRepository)

    //for features
    private val featureDao = database.featureDao()
    val featureRepository = FeatureRepository(featureDao)

    //for transcript
    private val transcriptApi = TranscriptApi(httpClient)
    private val studyProgramApi = StudyProgramApi(httpClient)
    private val transcriptRepository = TranscriptRepository(transcriptApi, studyProgramApi)
    val transcriptUseCase = TranscriptUseCase(transcriptRepository)
}