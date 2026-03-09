package org.example.project.presentations.screen.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.project.domain.model.FeatureUiModel
import org.example.project.domain.repository.FeatureRepository

class FeaturesViewModel(
    private val featureRepository: FeatureRepository
) : ViewModel() {
    private val _isEditing = MutableStateFlow(false)
    val isEditing = _isEditing.asStateFlow()

    private val _showExitDialog = MutableStateFlow(false)
    val showExitDialog = _showExitDialog.asStateFlow()

    private val _event = Channel<FeatureUiEvent>()
    val event = _event.receiveAsFlow()

    val quickAccessList = featureRepository.getQuickAccessList()
        .onStart { featureRepository.seedDefaultsIfNeeded() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        initQuickAccessList()
    }

    private fun initQuickAccessList() {
        viewModelScope.launch {
            featureRepository.seedDefaultsIfNeeded()
        }
    }

    fun toggleEditMode() {
        _isEditing.value = !_isEditing.value
    }

    fun removeFromQuickAccess(feature: FeatureUiModel) {
        viewModelScope.launch {
            featureRepository.removeFromQuickAccess(feature)
        }
    }

    fun addToQuickAccess(feature: FeatureUiModel) {
        viewModelScope.launch {
            featureRepository.addToQuickAccess(feature, quickAccessList.value.size)
        }
    }

    fun onBack(){
        if (_isEditing.value){
            _showExitDialog.value = true
        }else {
            sendUiEvent(FeatureUiEvent.OnBack)
        }
    }

    fun onDismissExitDialog(){
        _showExitDialog.value = false
    }

    private fun sendUiEvent(event: FeatureUiEvent){
        viewModelScope.launch {
            _event.trySend(event)
        }
    }
}