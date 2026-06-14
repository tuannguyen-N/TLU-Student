package org.example.project.presentations.screen.class_signed_up

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
import org.example.project.data.remote.dto.day_schedule.ScheduleData
import org.example.project.data.remote.dto.week_schedule.CourseClass
import org.example.project.domain.model.AppResult
import org.example.project.domain.repository.EnrollmentRepository

class SignedUpClassesViewModel(
    private val enrollmentRepository: EnrollmentRepository,
    private val semesterId: Int
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignedUpClassesState())
    val uiState = _uiState.asStateFlow()
    private val _event = Channel<SignedUpEvent>()
    val event = _event.receiveAsFlow()

    init {
        observeEnrolledClasses()
        fetchEnrollmentSchedule(semesterId)
    }

    private fun observeEnrolledClasses() {
        enrollmentRepository.enrolledClasses.onEach { enrollmentList ->
            val courseClasses = enrollmentList.map { enroll ->
                CourseClass(
                    classCode = enroll.classCode,
                    dayOfWeek = enroll.dayOfWeek,
                    subjectName = enroll.subjectName,
                    subjectCode = enroll.subjectCode,
                    credits = enroll.credits,
                    startPeriod = enroll.startPeriod,
                    endPeriod = enroll.endPeriod,
                    startTime = enroll.startTime,
                    endTime = enroll.endTime,
                    room = enroll.room,
                    lecturer = enroll.lecturer
                )
            }

            _uiState.update { it.copy(courseClasses = ScheduleData(courseClasses)) }
        }.launchIn(viewModelScope)
    }

    private fun fetchEnrollmentSchedule(semesterId: Int) {
        viewModelScope.launch {
            enrollmentRepository.getEnrollmentSchedule(semesterId)
        }
    }

    fun onDeleteClass(subjectCode: String, classCode: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val subjectResult = enrollmentRepository.getAllCourseEnrollment()) {
                is AppResult.Success -> {
                    val subject =
                        subjectResult.data.subjects.firstOrNull { it.subjectCode == subjectCode }
                    val subjectId = subject?.id ?: run {
                        _event.trySend(SignedUpEvent.CancelClassFailure("Không tìm thấy môn học"))
                        return@launch
                    }

                    when (val classesResult =
                        enrollmentRepository.getSubjectEnrollment(subjectId, semesterId)) {
                        is AppResult.Success -> {
                            val matched =
                                classesResult.data.firstOrNull { it.classCode == classCode }
                            val classId = matched?.id ?: run {
                                _event.trySend(SignedUpEvent.CancelClassFailure("Không tìm thấy lớp để hủy"))
                                return@launch
                            }

                            when (val cancelResult =
                                enrollmentRepository.cancelEnrollmentClass(classId)) {
                                is AppResult.Success -> {
                                    _event.trySend(SignedUpEvent.CancelClassSuccess(classCode))
                                    fetchEnrollmentSchedule(semesterId)
                                }

                                is AppResult.Failure -> {
                                    _event.trySend(
                                        SignedUpEvent.CancelClassFailure(
                                            cancelResult.message ?: ""
                                        )
                                    )
                                }
                            }
                        }

                        is AppResult.Failure -> {
                            _event.trySend(
                                SignedUpEvent.CancelClassFailure(
                                    classesResult.message ?: ""
                                )
                            )
                        }
                    }
                }

                is AppResult.Failure -> {
                    _event.trySend(SignedUpEvent.CancelClassFailure(subjectResult.message ?: ""))
                }
            }
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}