package org.example.project.presentations.screen.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.domain.model.NotificationUiModel
import org.example.project.domain.repository.NotificationRepository

class NotificationViewModel(
    private val notificationRepository: NotificationRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(NotificationState())
    val uiState = _uiState.asStateFlow()

    private val tabSenderKeys = mapOf(0 to null, 1 to "SYSTEM", 2 to "LECTURER", 3 to "FACULTY")

    private val _tabPagination = MutableStateFlow(
        tabSenderKeys.values.associateWith { TabPaginationState() }
    )

    val allNotifications = notificationRepository.allNotifications
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val systemNotifications = notificationRepository.systemNotifications
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val lecturerNotifications = notificationRepository.lecturerNotifications
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val facultyNotifications = notificationRepository.facultyNotifications
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val unreadMap = combine(
        allNotifications,
        systemNotifications,
        lecturerNotifications,
        facultyNotifications
    ) { all, system, lecturer, faculty ->
        mapOf(
            0 to all.any { !it.isRead },
            1 to system.any { !it.isRead },
            2 to lecturer.any { !it.isRead },
            3 to faculty.any { !it.isRead }
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    val filteredNotifications = uiState
        .map { it.selectedTab }
        .distinctUntilChanged()
        .flatMapLatest { tab ->
            when (tab) {
                1 -> systemNotifications
                2 -> lecturerNotifications
                3 -> facultyNotifications
                else -> allNotifications
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val currentTabPagination = combine(
        uiState.map { it.selectedTab }.distinctUntilChanged(),
        _tabPagination
    ) { tab, paginationMap ->
        paginationMap[tabSenderKeys[tab]] ?: TabPaginationState()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TabPaginationState())

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            val results = notificationRepository.getInitialNotifications()
            _tabPagination.update { current ->
                current.toMutableMap().apply {
                    results.forEach { (sender, result) ->
                        result.onSuccess { hasMore ->
                            this[sender] = TabPaginationState(currentPage = 1, hasMore = hasMore)
                        }
                    }
                }
            }
        }
    }

    fun onLoadMore() {
        val currentTab = _uiState.value.selectedTab
        val sender = tabSenderKeys[currentTab]
        val pagination = _tabPagination.value[sender] ?: return

        if (pagination.isLoadingMore || !pagination.hasMore) return

        viewModelScope.launch {
            updateTabPagination(sender) { copy(isLoadingMore = true) }
            try {
                notificationRepository.getNotifications(
                    sender = sender,
                    forceRefresh = false,
                    page = pagination.currentPage
                ).onSuccess { hasMore ->
                    updateTabPagination(sender) {
                        copy(currentPage = currentPage + 1, hasMore = hasMore)
                    }
                }
            } finally {
                updateTabPagination(sender) { copy(isLoadingMore = false) }
            }
        }
    }

    fun onRefreshData() {
        viewModelScope.launch {
            updateState { copy(isRefreshing = true) }
            try {
                loadInitialData()
            } finally {
                delay(1000L)
                updateState { copy(isRefreshing = false) }
            }
        }
    }

    fun onTabSelected(index: Int) {
        updateState { copy(selectedTab = index) }
    }

    fun onMarkAllRead() {
        viewModelScope.launch {
            notificationRepository.insertReadNotifications(filteredNotifications.value)
        }
    }

    fun onRead(notification: NotificationUiModel) {
        if (notification.isRead) return

        viewModelScope.launch {
            notificationRepository.insertReadNotification(notification)
        }
    }

    fun onShowBottomSheet() {
        updateState { copy(isShowBottomSheet = true) }
    }

    fun onHideBottomSheet() {
        updateState { copy(isShowBottomSheet = false) }
    }

    private fun updateTabPagination(
        sender: String?,
        update: TabPaginationState.() -> TabPaginationState
    ) {
        _tabPagination.update { current ->
            current.toMutableMap().apply {
                this[sender] = (this[sender] ?: TabPaginationState()).update()
            }
        }
    }

    private fun updateState(newState: NotificationState.() -> NotificationState) {
        _uiState.update(newState)
    }
}