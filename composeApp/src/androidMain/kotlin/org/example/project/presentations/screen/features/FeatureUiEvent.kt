package org.example.project.presentations.screen.features

sealed interface FeatureUiEvent {
    object OnBack: FeatureUiEvent
}