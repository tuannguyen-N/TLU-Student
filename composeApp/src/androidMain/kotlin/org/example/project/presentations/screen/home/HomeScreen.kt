package org.example.project.presentations.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.example.project.domain.model.AlertUiModel
import org.example.project.domain.model.FeatureUiModel
import org.example.project.domain.model.NewAndEventUiModel
import org.example.project.domain.model.ScheduleClassUiModel
import org.example.project.presentations.screen.home.components.AlertList
import org.example.project.presentations.screen.home.components.FeatureList
import org.example.project.presentations.screen.home.components.HomeHeader
import org.example.project.presentations.screen.home.components.NewsAndEventsList
import org.example.project.presentations.screen.home.components.ScheduleClassList
import org.example.project.presentations.theme.LocalExtendedColors

@Preview
@Composable
fun HomeScreen(
    onOpenProfileScreen: () -> Unit = {}, onOpenNotificationScreen: () -> Unit = {}
) {
    val alerts = remember { AlertUiModel.getDemoList() }
    val schedule = remember { ScheduleClassUiModel.getDataDemo() }
    val features = remember { FeatureUiModel.getDemoList() }
    val newAndEvent = remember { NewAndEventUiModel.getDataDemo() }
    Scaffold(
        containerColor = LocalExtendedColors.current.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            HomeHeader(
                onOpenProfile = onOpenProfileScreen, onOpenNotification = onOpenNotificationScreen
            )
        }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
                AlertList(
                    items = alerts,
                    onClickAction = {},
                    modifier = Modifier.padding(top = 15.dp),
                )
                ScheduleClassList(
                    onClickAll = {
                        // TODO:
                    }, items = schedule, modifier = Modifier.padding(horizontal = 15.dp)
                )

                FeatureList(
                    items = features,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .padding(top = 15.dp)
                )

                NewsAndEventsList(
                    items = newAndEvent, modifier = Modifier.padding(top = 15.dp)
                )
                Spacer(modifier = Modifier.height(200.dp))
            }
        }
    }
}