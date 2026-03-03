package org.example.project.presentations.screen.timetable.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TimetableScrollableArea(
    onShowSubjectDetail: () -> Unit,
) {

    val verticalScroll = rememberScrollState()
    val horizontalScroll = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(horizontalScroll)
            .verticalScroll(verticalScroll)
    ) {
        TimetableGrid(
            onShowSubjectDetail = onShowSubjectDetail
        )
    }
}