package org.example.project.presentations.screen.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.example.project.domain.model.EventAndNewUiModel
import org.example.project.presentations.theme.LocalExtendedColors

@Composable
fun NewsAndEventsList(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    items: List<EventAndNewUiModel>,
    onClickAll: () -> Unit,
    onOpenNews: (String) -> Unit
) {
    val pageCount = Int.MAX_VALUE
    val startPage = pageCount / 2

    val pagerState = rememberPagerState(
        initialPage = startPage
    ) {
        pageCount
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = modifier
                .padding(horizontal = 15.dp)
                .padding(bottom = 10.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Tin tức và sự kiện",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Tất cả",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = LocalExtendedColors.current.fontBlue,
                modifier = Modifier.clickable {
                    onClickAll()
                }
            )
        }

        HorizontalPager(
            state = pagerState,
            pageSpacing = 10.dp,
            contentPadding = PaddingValues(horizontal = 20.dp),
            pageSize = PageSize.Fixed(220.dp)
        ) { page ->
            if (isLoading) {
                repeat(3) {
                    ShimmerNew()
                }
            } else {
                if (items.isNotEmpty()) {
                    val realIndex = page % items.size
                    val item = items[realIndex]

                    NewAndEventCard(
                        item = item,
                        onOpenNews = onOpenNews,
                    )
                }
            }
        }
    }
}