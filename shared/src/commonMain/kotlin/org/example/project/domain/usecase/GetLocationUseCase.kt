package org.example.project.domain.usecase

import org.example.project.domain.model.LocationData
import org.example.project.domain.repository.LocationRepository

class GetLocationUseCase(
    private val locationRepository: LocationRepository
) {
    operator fun invoke(): LocationData {
        return locationRepository.getLastKnownLocation()
    }
}