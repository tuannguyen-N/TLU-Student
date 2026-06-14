package org.example.project.presentations.screen.class_sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.remote.dto.enrollment_course_classes.CourseClassEnrollmentData
import org.example.project.domain.model.AppResult
import org.example.project.domain.model.CourseFilter
import org.example.project.domain.model.CourseItem
import org.example.project.domain.model.CourseStatus
import org.example.project.domain.repository.EnrollmentRepository
import org.example.project.domain.repository.SemesterRepository
import org.example.project.presentations.utils.withDelayedLoading

class ClassSignUpViewModel(
    private val enrollmentRepository: EnrollmentRepository,
    private val semesterRepository: SemesterRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(ClassSignUpState())
    val uiState = _uiState.asStateFlow()

    private val _event = Channel<ClassSignUpEvent>()
    val event = _event.receiveAsFlow()

    init {
        observeEnrolledClasses()
        initData()
    }

    private fun observeEnrolledClasses() {
        enrollmentRepository.enrolledClasses
            .onEach { enrolledClasses ->
                _uiState.update { it.copy(enrolledClasses = enrolledClasses) }
                updateSummary()
            }
            .launchIn(viewModelScope)
    }

    private fun initData() {
        viewModelScope.launch {
            getSemesterInfo()
            fetchCourses()
        }
    }

    private suspend fun getSemesterInfo() {
        semesterRepository.getSemesterPeriod().onSuccess { semester ->
            _uiState.update {
                it.copy(
                    semesterName = semester.semesterName,
                    semesterId = semester.semesterId,
                    enrollmentStartTime = semester.startTime,
                    enrollmentEndTime = semester.endTime
                )
            }
        }
            .onFailure { _uiState.update { it.copy(semesterName = "") } }
    }

    private suspend fun fetchCourses() {
        _uiState.update { it.copy(isLoading = true, error = null) }

        when (val result = enrollmentRepository.getAllCourseEnrollment()) {
            is AppResult.Success -> {
                val subjects = result.data.subjects
                val semesterId = result.data.semesterId
                val courseItems = subjects.map { subject ->
                    CourseItem(
                        code = subject.subjectCode,
                        credits = subject.credits,
                        name = subject.subjectName,
                        id = subject.id,
                        semesterId = semesterId,
                        status = CourseStatus.AVAILABLE,
                        category = if (subject.isRequired) CourseFilter.REQUIRED else CourseFilter.ELECTIVE
                    )
                }
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        courses = courseItems,
                    )
                }
                enrollmentRepository.getEnrollmentSchedule(semesterId)
            }

            is AppResult.Failure -> {
                _uiState.update { it.copy(isLoading = false, error = result.message) }
            }
        }

        updateSummary()
    }

    fun openSelectedScheduleDialog(course: CourseItem) {
        viewModelScope.launch {
            withDelayedLoading(onLoading = { loading ->
                _uiState.update { it.copy(isDialogLoading = loading) }
            }) {
                _uiState.update { it.copy(showDialog = true, selectedCourseTitle = course.name) }

                when (val result =
                    enrollmentRepository.getSubjectEnrollment(course.id, course.semesterId)) {
                    is AppResult.Success -> {
                        _uiState.update { it.copy(courseClasses = result.data) }
                    }

                    is AppResult.Failure -> {
                        _uiState.update { it.copy(error = result.message) }
                    }
                }
            }
        }
    }

    fun enrollClass(courseClass: CourseClassEnrollmentData) {
        viewModelScope.launch {
            val semesterId = _uiState.value.semesterId ?: run {
                sendEvent(ClassSignUpEvent.EnrollClassFailure("Không xác định được học kỳ"))
                return@launch
            }
            _uiState.update { it.copy(showDialog = false, isEnrolling = true) }
            enrollmentRepository.enrollClass(
                courseClassId = courseClass.id,
                studyProgramId = semesterId,
            ).fold(
                onSuccess = {
                    sendEvent(ClassSignUpEvent.EnrollClassSuccess(courseClass.className))
                },
                onFailure = {
                    sendEvent(ClassSignUpEvent.EnrollClassFailure(it.message ?: "Đã xảy ra lỗi"))
                }
            )
            enrollmentRepository.getEnrollmentSchedule(semesterId)
            _uiState.update { it.copy(isEnrolling = false) }
        }
    }

    private fun updateSummary() {
        val groupedSubjects = _uiState.value.enrolledClasses.groupBy { it.subjectCode }
        _uiState.update {
            it.copy(
                totalSubjects = groupedSubjects.size,
                totalCredits = groupedSubjects.values.sumOf { classes -> classes.first().credits }
            )
        }
    }

    fun dismissDialog() {
        _uiState.update { it.copy(showDialog = false) }
    }

    private fun sendEvent(event: ClassSignUpEvent) {
        _event.trySend(event)
    }
}