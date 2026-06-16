package org.example.project.presentations.screen.tuition_payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import org.example.project.domain.repository.NotificationRepository
import org.example.project.domain.repository.PaymentRepository
import org.example.project.domain.repository.TuitionRepository
import org.example.project.domain.usecase.StudentUseCase

class TuitionPaymentViewModelFactory(
    private val tuitionRepository: TuitionRepository,
    private val paymentRepository: PaymentRepository,
    private val notificationRepository: NotificationRepository,
    private val studentUseCase: StudentUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(
        modelClass: Class<T>,
        extras: CreationExtras
    ): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(TuitionPaymentViewModel::class.java)) {
            val savedStateHandle = extras.createSavedStateHandle()
            return TuitionPaymentViewModel(
                tuitionRepository,
                paymentRepository,
                savedStateHandle,
                notificationRepository,
                studentUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}