package org.example.project.presentations.screen.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.example.project.domain.repository.FeatureRepository

class FeaturesViewModelFactory(
    private val featureRepository: FeatureRepository
): ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeaturesViewModel::class.java)) {
            return FeaturesViewModel(featureRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}