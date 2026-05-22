package org.example.project.presentations.screen.attendance_checking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import org.example.project.domain.repository.AttendanceRepository
import org.example.project.domain.repository.LocationRepository
import org.example.project.domain.usecase.GetLocationUseCase

class CameraQrViewModelFactory(
    private val attendanceRepository: AttendanceRepository,
    private val getLocationUseCase: GetLocationUseCase,
    private val locationRepository: LocationRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(CameraQrViewModel::class.java)) {
            return CameraQrViewModel(
                attendanceRepository,
                getLocationUseCase,
                locationRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
