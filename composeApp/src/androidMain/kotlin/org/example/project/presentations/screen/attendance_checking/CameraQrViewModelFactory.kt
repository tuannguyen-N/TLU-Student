package org.example.project.presentations.screen.attendance_checking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CameraQrViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(CameraQrViewModel::class.java)) {
            return CameraQrViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
